import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/empty';
import {catchError, map, tap} from 'rxjs/operators';

type EventType = { type: string, percentOff: number }

@Injectable()
export class LookupsService {
    private _eventTypes: Observable<string[]>;
    private _filePurposes: Observable<string[]>;
    private _departments: Observable<string[]>;
    private _employeeRanks: Observable<string[]>;
    private _mimeTypes: Observable<string[]>;
    private _requestStatuses: Observable<string[]>;

    constructor(private http: HttpClient) {
        const baseUrl = 'http://localhost:8085/trms/lookups';
        const eventTypesUrl = `${baseUrl}/eventTypes`;
        const filePurposesUrl = `${baseUrl}/filePurposes`;
        const departmentsUrl = `${baseUrl}/departments`;
        const ranksUrl = `${baseUrl}/employeeRanks`;
        const mimesUrl = `${baseUrl}/mimeTypes`;
        const statusesUrl = `${baseUrl}/requestStatuses`;

        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true
        };

        this._eventTypes = this.http.get<EventType[]>(eventTypesUrl, httpOptions)
            .pipe(
                map((eTypes: EventType[]) => {
                    return eTypes.map(evt => evt.type);
                }),
                tap(types => {
                }),
                catchError(this.handleError<any>('get event types'))
            );

        this._filePurposes = this.http.get<string[]>(filePurposesUrl, httpOptions)
            .pipe(
                tap(purposes => {
                }),
                catchError(this.handleError<any>('get file purposes'))
            );

        this._departments = this.http.get<string[]>(departmentsUrl, httpOptions)
            .pipe(
                tap(departments => {
                }),
                catchError(this.handleError<any>('get departments'))
            );

        this._employeeRanks = this.http.get<string[]>(ranksUrl, httpOptions)
            .pipe(
                tap(rank => {
                }),
                catchError(this.handleError<any>('get employee ranks'))
            );

        this._mimeTypes = this.http.get<string[]>(mimesUrl, httpOptions)
            .pipe(
                tap(mimeType => {
                }),
                catchError(this.handleError<any>('get mime types'))
            );

        this._requestStatuses = this.http.get<string[]>(statusesUrl, httpOptions)
            .pipe(
                tap(status => {
                }),
                catchError(this.handleError<any>('get statusesUrl'))
            );
    };

    getRequestStatuses(): Observable<string []> {
        return this._requestStatuses;
    }

    getMimeTypes(): Observable<string[]> {
        return this._mimeTypes;
    }

    getEmployeeRanks(): Observable<string[]> {
        return this._employeeRanks;
    }

    getDepartments(): Observable<string[]> {
        return this._departments;
    }

    getFilePurposes(): Observable<string[]> {
        return this._filePurposes;
    }

    getEventTypes(): Observable<string[]> {
        return this._eventTypes;
    }

    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error);
            // Let the app keep running by returning an empty result.
            return Observable.empty<T>();
        };
    }
}

