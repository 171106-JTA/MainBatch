import {Component} from '@angular/core';
import {RequestService} from '../../services/request.service';
import {ReimbursementRequest} from '../../models/reimbursement-request';
import {CredentialsService} from "../../services/credentials.service";
import {Router} from "@angular/router";


@Component({
    selector: 'app-user-requests',
    templateUrl: 'user-requests.component.html',
    styleUrls: ['user-requests.component.css']
})
export class UserRequestsComponent {
    filedRequests: ReimbursementRequest[] = null;
    allRequests: ReimbursementRequest[] = null;
    departmentRequests: ReimbursementRequest[] = null;
    waitingOnMe: ReimbursementRequest[] = null;

    isBenco: boolean = false;
    isDeptHead: boolean = false;

    constructor(private reqService: RequestService, private credentialsService: CredentialsService, private router: Router) {
        if (!this.credentialsService.isLoggedIn()) {
            this.router.navigate(['login']);
            return;
        }

        let emp = this.credentialsService.getCredentials();

        this.isBenco = emp.rank === 'Benefits Coordinator';
        this.isDeptHead = emp.rank === 'Department Head';

        console.log('fetching my filed requests');
        this.reqService.getFiledRequests('all').subscribe(
            data => {
                console.log(data);
                this.filedRequests = data;
            },
            error => console.warn(error),
            () => {}
        );

        console.log('fetching requests pending on me');
        this.reqService.getRequestsWaitingOnMe(null, null).subscribe(
            data => {
                this.waitingOnMe = data;
            },
            error => console.warn(error),
            () => {}
        );
        if (this.isBenco) {
            console.log('fetching ALL requests');
            this.reqService.getRequests(null, null).subscribe(
                data => {
                    this.allRequests = data;
                },
                error => console.warn(error),
            );
        }
        if (this.isDeptHead || this.isBenco) {
            console.log("fetching by dept");
            this.reqService.getRequests(emp.department, null).subscribe(
                data => {
                    this.departmentRequests = data;
                },
                error => console.warn(error),
            )
        }
    }
}

