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
    constructor(private http: HttpClient, private credentialsService: CredentialsService) {}

    /**
     * possible statuses: all, pending, then everything in lookup-table
     * @param {string} status
     * @returns {Observable<ReimbursementRequest[]>}
     */
    public getFiledRequests(status: string): Observable<ReimbursementRequest[]> {
        let params = new HttpParams();
        params.append("wantsFiledRequests", "true"); // value is ignored, for the email field
        params.append("status", status);
        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true,
            params: params
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data:any[]) => data.map(reqJson => new ReimbursementRequest(reqJson)));
    }

    public getRequestsByDepartment(department: string): Observable<ReimbursementRequest[]> {
        let params = new HttpParams();
        params.append("department", department);
        params.append("status", "pending");
        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true,
            params: params
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data:any[]) => data.map(reqJson => new ReimbursementRequest(reqJson)));
    }

    public getAllRequests(status: string): Observable<ReimbursementRequest[]> {
        const httpOptions = {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            withCredentials: true
        };
        return this.http.get<ReimbursementRequest[]>(requestsUrl, httpOptions)
            .map((data:any[]) => data.map(reqJson => new ReimbursementRequest(reqJson)));
    }

}

