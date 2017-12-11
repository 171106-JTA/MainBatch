import {Component, Input} from '@angular/core';
import {ReimbursementRequest} from '../../models/reimbursement-request';


@Component ({
    selector: 'app-requests',
    templateUrl: 'requests.component.html',
    styleUrls: ['requests.component.css'],
    inputs: ['requests', 'tableTitle']
})

export class RequestsComponent {

    @Input()
    requests: ReimbursementRequest[];

    @Input()
    tableTitle: string;

    constructor() {
    }
}
