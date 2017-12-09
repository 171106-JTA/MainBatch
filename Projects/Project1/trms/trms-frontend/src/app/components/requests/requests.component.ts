import {Component, Input} from '@angular/core';
import {ReimbursementRequest} from '../../models/reimbursement-request';


@Component ({
    selector: 'app-requests',
    templateUrl: 'requests.component.html',
    styleUrls: ['requests.component.css'],
    inputs: ['requests']
})

export class RequestsComponent {
    public isHidden = false;

    @Input()
    requests: ReimbursementRequest[];

    constructor() {
    }
}
