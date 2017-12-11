import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/empty';
import 'rxjs/add/operator/map';
import {ReimbursementRequest} from '../models/reimbursement-request';
import {CredentialsService} from './credentials.service';

const requestsUrl = 'http://localhost:8085/trms/employee/requests';

@Injectable()
export class RequestService {
    constructor(private http: HttpClient, private credentialsService: CredentialsService) {
    }

    /**
     * possible statuses: all, pending, then everything in lookup-table
     *
     * note: request params are fuc**** immutable
     * @param {string} status
     * @returns {Observable<ReimbursementRequest[]>}
     */
    public getFiledRequests(status: string): Observable<ReimbursementRequest[]> {
        let params = new HttpParams();
        params = params.append("wantsFiledRequests", "true"); // value is ignored, for the email field
        if (status != null)
            params = params.append("status", status);
        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true,
            params: params
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data: any[]) => data.map(reqJson => {
                return new ReimbursementRequest(reqJson);
            }));
    }

    /**
     * get all requests waiting on employee with email
     *
     * @param department
     * @param {string} status
     * @returns {Observable<ReimbursementRequest[]>}
     */
    public getRequestsWaitingOnMe(department: string, status: string): Observable<ReimbursementRequest[]> {
        let params = new HttpParams();
        params = params.append("wantsWaitingOnMe", "true");
        if (status != null)
            params = params.append("status", status);
        if (department != null)
            params = params.append("department", department);

        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true,
            params: params
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data: any[]) => data.map(reqJson => {
                return new ReimbursementRequest(reqJson);
            }));
    }

    /**
     * Allows specifying desired request id
     *
     * or gets all requests filtered by given department, status;
     * if a category is null, does not filter in that category
     *
     * @param id
     * @param {string} department
     * @param {string} status
     * @returns {Observable<ReimbursementRequest[]>}
     */
    public getRequests(department: string, status: string): Observable<ReimbursementRequest[]> {
        let params = new HttpParams();
        if (status !== null)
            params = params.append("status", status);
        if (department !== null)
            params = params.append("department", department);

        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            params: params,
            withCredentials: true
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data: any[]) => data.map(reqJson => {
                return new ReimbursementRequest(reqJson);
            }));
    }
    public getRequest(id: number): Observable<ReimbursementRequest> {
        let params = new HttpParams();
        if (id !== null)
            params = params.append("id", '' + id);

        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            params: params,
            withCredentials: true
        };
        return this.http.get<ReimbursementRequest>(requestsUrl, httpOptions)
            .map((data: any) => {
                return new ReimbursementRequest(data);
            });
    }

}

