import {Component} from '@angular/core';
import {RequestService} from '../../services/request.service';
import {ReimbursementRequest} from '../../models/reimbursement-request';

@Component({
    selector: 'app-user-requests',
    templateUrl: 'user-requests.component.html',
})


export class UserRequestsComponent {
    filedRequests: ReimbursementRequest[] = [];
    allRequests: ReimbursementRequest[] = [];
    departmentRequests: ReimbursementRequest[] = [];

    constructor(private reqService: RequestService) {
        this.reqService.getAllRequests('all').subscribe(
            data => {
                this.allRequests = data;
            },
            error => console.warn(error),
            () => {
            }
        );
        this.reqService.getFiledRequests('all').subscribe(
            data => {
                this.filedRequests = data;
            },
            error => console.warn(error),
            () => {
            }
        );
        this.reqService.getRequestsByDepartment('Slytherin').subscribe(
            data => {
                this.departmentRequests = data;
            },
            error => console.warn(error),
            () => {
            }
        )
    }
}

