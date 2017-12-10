import {Component} from '@angular/core';
import {RequestService} from '../../services/request.service';
import {ReimbursementRequest} from '../../models/reimbursement-request';
import {CredentialsService} from "../../services/credentials.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-user-requests',
    templateUrl: 'user-requests.component.html',
})


export class UserRequestsComponent {
    filedRequests: ReimbursementRequest[] = null;
    allRequests: ReimbursementRequest[] = null;
    departmentRequests: ReimbursementRequest[] = null;
    waitingOnMe: ReimbursementRequest[] = null;

    constructor(private reqService: RequestService, private credentialsService: CredentialsService, private router: Router) {
        if (!this.credentialsService.isLoggedIn()) {
            this.router.navigate(['login']);
            return;
        }

        let emp = this.credentialsService.getCredentials();

        console.log("fetching my filed requests");
        this.reqService.getFiledRequests('all').subscribe(
            data => {
                console.log(data);
                this.filedRequests = data;
            },
            error => console.warn(error),
            () => {}
        );
        if (emp.rank === 'Benefits Coordinator') {
            console.log("fetching ALL requests");
            this.reqService.getAllRequests(null, null).subscribe(
                data => {
                    console.log(data);
                    this.allRequests = data;
                },
                error => console.warn(error),
            );
        }
        if (emp.rank === 'Benefits Coordinator' || emp.rank === 'Department Head') {
            console.log("fetching by dept");
            this.reqService.getAllRequests('Slytherin', null).subscribe(
                data => {
                    console.log(data);
                    this.departmentRequests = data;
                },
                error => console.warn(error),
            )
        }
    }
}

