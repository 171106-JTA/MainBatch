import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../../services/request.service";
import {ReimbursementRequest} from "../../models/reimbursement-request";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LookupsService} from "../../services/lookups.service";
import {RequestFile} from "../../models/request-file";
import {DatePipe} from "@angular/common";
import {Employee} from "../../models/employee";
import {CredentialsService} from "../../services/credentials.service";
import {HttpClient} from "@angular/common/http";

const updateRequestUrl = 'http://localhost:8085/trms/employee/updateRequest';
const requestFileUrl = 'http://localhost:8085/trms/employee/requestFile';


@Component({
    selector: 'app-request-details',
    templateUrl: './request-details.component.html',
    styleUrls: ['./request-details.component.css']
})
export class RequestDetailsComponent {

    eventTypes: string[];
    validMimeTypes: string[];
    filePurposes: string[] = null;

    eventFile: Blob = null;
    eventFileName: string = null;
    eventFileMimeType: string = null;

    request: ReimbursementRequest = null;

    employee: Employee = null;
    canApprove: boolean = false;
    canUpdate: boolean = false;

    newFileForm: FormGroup = new FormGroup({
        eventFilePurpose: new FormControl('', Validators.required),
        eventFile: new FormControl('', Validators.required)
    });

    // todo: support editing of all fields
    updateRequestForm: FormGroup = new FormGroup({
        eventType: new FormControl('', Validators.required),
        timeFiled: new FormControl('', Validators.required),
        eventStartDate: new FormControl('', Validators.required),
        eventEndDate: new FormControl('', Validators.required),
        eventAddress: new FormControl('', Validators.required),
        eventCity: new FormControl('', Validators.required),
        eventState: new FormControl('', Validators.required),
        eventZip: new FormControl('', Validators.required),
        eventPrice: new FormControl('', Validators.required),
        eventFunding: new FormControl('', Validators.required),
        exceedsFunds: new FormControl('', Validators.required),
        eventDescription: new FormControl('', Validators.required),
        urgency: new FormControl('', Validators.required),
        filerEmail: new FormControl('', Validators.required),
        status: new FormControl('', Validators.required)
    });

    private populateForm() {
        this.updateRequestForm.controls.eventType.setValue(this.request.eventType);
        this.updateRequestForm.controls.timeFiled.setValue(this.datePipe.transform(this.request.timeFiled, 'yyyy-MM-ddThh:mm'));
        this.updateRequestForm.controls.eventStartDate.setValue(this.datePipe.transform(this.request.eventStart.getDate(), 'yyyy-MM-dd'));
        this.updateRequestForm.controls.eventEndDate.setValue(this.datePipe.transform(this.request.eventEnd.getDate(), 'yyyy-MM-dd'));
        this.updateRequestForm.controls.eventAddress.setValue(this.request.address);
        this.updateRequestForm.controls.eventCity.setValue(this.request.city);
        this.updateRequestForm.controls.eventState.setValue(this.request.state);
        this.updateRequestForm.controls.eventZip.setValue(this.request.zip);
        this.updateRequestForm.controls.eventPrice.setValue(this.request.cost);
        this.updateRequestForm.controls.eventFunding.setValue(this.request.funding);
        this.updateRequestForm.controls.exceedsFunds.setValue(this.request.exceedsFunds);
        this.updateRequestForm.controls.eventDescription.setValue(this.request.description);
        this.updateRequestForm.controls.filerEmail.setValue(this.request.filerEmail);
        this.updateRequestForm.controls.status.setValue(this.request.status);
    }


    constructor(private lookups: LookupsService, private router: Router, private route: ActivatedRoute, private http: HttpClient,
                private requestService: RequestService, private datePipe: DatePipe, private credentials: CredentialsService) {
        if (!this.credentials.isLoggedIn())
            this.router.navigate(['/login']);
        else
            this.employee = this.credentials.getCredentials();

        this.route.params.subscribe(
            params => {
                this.requestService.getRequest(+params['id']).subscribe(
                    request => {
                        this.request = request;
                        this.canUpdate = this.employee.email === this.request.filerEmail;
                        this.canApprove = (this.employee.rank === 'Supervisor') ||  // todo filter this better
                            (this.employee.rank === 'Department Head') ||
                            (this.employee.rank === 'Benefits Coordinator');
                        this.populateForm();
                    },
                    err => {
                        console.log('error fetching id param');
                        console.log(err);
                    }
                );
            },
            err => {
                console.log('error fetching request with id');
                console.log(err);
            }
        );

        this.lookups.getEventTypes().subscribe(
            data => {
                this.eventTypes = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

        this.lookups.getFilePurposes().subscribe(
            data => {
                this.filePurposes = data;
            },
            err => {
                console.error(err);
            },
        );


        this.lookups.getMimeTypes().subscribe(
            data => {
                this.validMimeTypes = data;
            },
            err => {
                console.error(err);
            },
        );
    }

    public submitUpdates($event) {
        let formData = new FormData();

        for (let formControlKey in this.updateRequestForm.controls) {
            formData.append(formControlKey, this.updateRequestForm.get(formControlKey).value);
            console.log(formControlKey, this.updateRequestForm.get(formControlKey).value);
        }
        formData.append('id', ''+this.request.id);

        const httpOptions = {
            withCredentials: true,
        };
        this.http.post<ReimbursementRequest>(updateRequestUrl, formData, httpOptions).subscribe(
            data => {
                console.log(data);
                this.request = new ReimbursementRequest(data);
            },
            console.log
        );
    }

    public submitApproval($event) {
        let formData = new FormData();

        formData.append('id', ''+this.request.id);
        formData.append('action', 'approve');
        formData.append('approvalAmount', this.updateRequestForm.get('eventFunding').value);

        const httpOptions = {
            withCredentials: true,
        };
        this.http.post<ReimbursementRequest>(updateRequestUrl, formData, httpOptions).subscribe(
            data => {
                console.log(data);
                this.request = new ReimbursementRequest(data);
                this.canApprove = false;
                this.canUpdate = false;
            },
            console.log
        );
    }

    public submitDenial($event) {
        let formData = new FormData();

        formData.append('id', ''+this.request.id);
        formData.append('action', 'deny');
        formData.append('denialReason', "no reason provided; todo: fix this");

        const httpOptions = {
            withCredentials: true,
        };
        this.http.post<ReimbursementRequest>(updateRequestUrl, formData, httpOptions).subscribe(
            data => {
                console.log(data);
                this.request = new ReimbursementRequest(data);
                this.canApprove = false;
                this.canUpdate = false;
            },
            console.log
        );
    }


    public submitNewFile($event) {
        if (this.eventFile != null) {
            let formData = new FormData();
            formData.append('eventFile', this.eventFile, this.eventFileName);
            formData.append('eventFileName', this.eventFileName);
            formData.append('eventFileMimeType', this.eventFileMimeType);

            let filePurpose: string = this.newFileForm.get('eventFilePurpose').value;
            if (filePurpose == '')
                filePurpose = this.filePurposes[0];
            formData.set('eventFilePurpose', filePurpose);   // use set to overwrite
            formData.append('id', ''+this.request.id);

            let xhr = new XMLHttpRequest();
            xhr.withCredentials = true;
            xhr.onreadystatechange = () => {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log(xhr.response);
                    let data = JSON.parse(xhr.response);
                    this.router.navigate([`/requests/${data.id}`])
                }
                else
                    console.warn(xhr.response);
            };
            xhr.upload.onprogress = (event) => {
                let progress = Math.round(event.loaded / event.total * 100);
            };
            xhr.open('POST', requestFileUrl, true);
            xhr.send(formData);
        }
    }

    public onFileChange($event) {
        let rawFile: any = $event.target.files[0];
        if (rawFile != undefined) {
            this.eventFile = rawFile;
            this.eventFileName = rawFile.name;
            this.eventFileMimeType = rawFile.type;
        }
        else {
            this.eventFile = null;
        }
    }

}
