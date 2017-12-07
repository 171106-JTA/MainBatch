import {Component} from '@angular/core';
import {LookupsService} from '../../services/lookups.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';


const newRequestUrl = 'http://localhost:8085/trms/employee/newRequest';

@Component({
    selector: 'app-new-request',
    templateUrl: './new-request.component.html',
    styleUrls: ['./new-request.component.css']
})
export class NewRequestComponent {
    eventTypes: string[];
    validMimeTypes: string[];

    eventFile: Blob;
    eventFileName: string;
    eventFileMimeType: string;


    newRequestForm: FormGroup = new FormGroup({
        eventType: new FormControl('', Validators.required),
        eventStartDate: new FormControl('', Validators.required),
        eventEndDate: new FormControl('', Validators.required),
        eventAddress: new FormControl('', Validators.required),
        eventCity: new FormControl('', Validators.required),
        eventState: new FormControl('', Validators.required),
        eventZip: new FormControl('', Validators.required),
        eventPrice: new FormControl('', Validators.required),
        eventDescription: new FormControl(),
        eventFile: new FormControl() // doesn't work for file inputs, here for posterity
    });

    constructor(private lookups: LookupsService) {
        this.lookups.getEventTypes().subscribe(
            data => {
                this.eventTypes = data;
            },
            err => {
                console.error(err);
            },
            () => {}
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

    public submitNewRequest() {
        let formData = new FormData();

        for (let formControlKey in this.newRequestForm.controls) {
            formData.append(formControlKey, this.newRequestForm.get(formControlKey).value);
            console.log(formControlKey, this.newRequestForm.get(formControlKey).value); // todo: remove
        }
        formData.append('eventFile', this.eventFile, this.eventFileName);
        formData.append('eventFileName', this.eventFileName);
        formData.append('eventFileMimeType', this.eventFileMimeType);


        let xhr = new XMLHttpRequest();
        xhr.withCredentials = true;
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(xhr.response);
            }
            else
                console.warn(xhr.response);
        };
        xhr.upload.onprogress = (event) => {
            let progress = Math.round(event.loaded / event.total * 100);
        };
        xhr.open('POST', newRequestUrl, true);
        xhr.send(formData);
    }

    public onFileChange($event) {
        let rawFile: any = $event.target.files[0];

        /* todo: validate file mime type
        if (!(rawFile.mimeType in this.lookups.getMimeTypes()))
            console.error('invalid file type');
        */

        this.eventFile = rawFile;
        this.eventFileName = rawFile.name;
        this.eventFileMimeType = rawFile.type;
    }
}

