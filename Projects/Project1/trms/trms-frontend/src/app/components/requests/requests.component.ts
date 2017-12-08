import {Component} from '@angular/core';
import {RequestService} from '../../services/request.service';


@Component ({
    selector: 'app-requests',
    templateUrl: 'requests.component.html',
    styleUrls: []
})
export class RequestsComponent {
    constructor(private requestsService: RequestService) {
    }
}
