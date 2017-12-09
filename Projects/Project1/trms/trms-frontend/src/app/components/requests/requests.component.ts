import {Component, Input} from '@angular/core';
import {ReimbursementRequest} from '../../models/reimbursement-request';


@Component ({
    selector: 'app-requests',
    inputs: ['requests'],
    templateUrl: 'requests.component.html',
    styleUrls: [],
})

export class RequestsComponent {
    public isHidden = false;

    @Input()
    requests: ReimbursementRequest[];

    constructor() {
    }
}
