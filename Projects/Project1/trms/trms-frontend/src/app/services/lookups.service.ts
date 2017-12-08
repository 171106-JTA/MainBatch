import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/empty';
import 'rxjs/add/observable/of';
import {catchError, map, tap} from 'rxjs/operators';

type EventType = { type: string, percentOff: number }


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


@Injectable()
export class LookupsService {

    eventTypes: string[];
    filePurposes: string[];
    departments: string[];
    ranks: string[];
    mimeTypes: string[];
    statuses: string[];

    constructor(private http: HttpClient) {
    };

    /**
     * the first time we fetch will be from db; the fetched values are cached for subsequent calls
     * @returns {Observable<string[]>}
     */
    getRequestStatuses(): Observable<string []> {
        if (this.statuses === undefined) {
            return this.http.get<string[]>(statusesUrl, httpOptions)
                .pipe(
                    tap(statuses => {
                        this.statuses = statuses;
                        return statuses;
                    }),
                    catchError(this.handleError<any>('get statusesUrl'))
                );
        }
        return Observable.of(this.statuses);
    }

    getMimeTypes(): Observable<string[]> {
        if (this.mimeTypes === undefined)
            return this.http.get<string[]>(mimesUrl, httpOptions)
                .pipe(
                    tap(mimeTypes => {
                        this.mimeTypes = mimeTypes;
                        return mimeTypes;
                    }),
                    catchError(this.handleError<any>('get mime types'))
                );
        return Observable.of(this.mimeTypes);
    }

    getEmployeeRanks(): Observable<string[]> {
        if (this.ranks === undefined)
            return this.http.get<string[]>(ranksUrl, httpOptions)
                .pipe(
                    tap(ranks => {
                        this.ranks = ranks;
                        return ranks;
                    }),
                    catchError(this.handleError<any>('get employee ranks'))
                );
        return Observable.of(this.ranks);
    }

    getDepartments(): Observable<string[]> {
        if (this.departments === undefined)
            return this.http.get<string[]>(departmentsUrl, httpOptions)
                .pipe(
                    tap(depts => {
                        this.departments = depts;
                        return depts;
                    }),
                    catchError(this.handleError<any>('get departments'))
                );
        return Observable.of(this.departments);
    }

    getFilePurposes(): Observable<string[]> {
        if (this.filePurposes === undefined)
            return this.http.get<string[]>(filePurposesUrl, httpOptions)
                .pipe(
                    tap(purposes => {
                        this.filePurposes = purposes;
                        return purposes;
                    }),
                    catchError(this.handleError<any>('get file purposes'))
                );
        return Observable.of(this.filePurposes);
    }

    getEventTypes(): Observable<string[]> {
        if (this.eventTypes === undefined)
            return this.http.get<EventType[]>(eventTypesUrl, httpOptions)
                .pipe(
                    map((eTypes: EventType[]) => {
                        this.eventTypes = eTypes.map(evt => evt.type);
                        return this.eventTypes;
                    }),
                    tap(types => {
                    }),
                    catchError(this.handleError<any>('get event types'))
                );
        return Observable.of(this.eventTypes);
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

