import {Component} from '@angular/core';
import {LookupsService} from '../../services/lookups.service';

@Component({
    selector: 'app-new-request',
    templateUrl: './new-request.component.html',
    styleUrls: ['./new-request.component.css']
})


export class NewRequestComponent {
    private _eventTypes: string[];
    private _filePurposes: string[];
    private _departments: string[];
    private _employeeRanks: string[];
    private _mimeTypes: string[];
    private _requestStatuses: string[];


    constructor(private lookups: LookupsService) {
        this.lookups.getEventTypes().subscribe(
            data => {
                this._eventTypes = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

        this.lookups.getFilePurposes().subscribe(
            data => {
                this._filePurposes = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

         this.lookups.getDepartments().subscribe(
            data => {
                this._departments = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

        this.lookups.getEmployeeRanks().subscribe(
            data => {
                this._employeeRanks = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

        this.lookups.getMimeTypes().subscribe(
            data => {
                this._mimeTypes = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );

        this.lookups.getRequestStatuses().subscribe(
            data => {
                this._requestStatuses = data;
            },
            err => {
                console.error(err);
            },
            () => {
            }
        );
    }

    get eventTypes(): string[] {
        return this._eventTypes;
    }

    get filePurposes(): string[] {
        return this._filePurposes;
    }

    get departments(): string[] {
        return this._departments;
    }

    get employeeRanks(): string[] {
        return this._employeeRanks;
    }

    get mimeTypes(): string[] {
        return this._mimeTypes;
    }

    get requestStatuses(): string[] {
        return this._requestStatuses;
    }
}
