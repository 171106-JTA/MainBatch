import {Injectable} from '@angular/core';
import {Employee} from "../models/employee";
import {HttpClient} from "@angular/common/http";


const loginUrl = 'http://localhost:8085/trms/login';
const logoutUrl = 'http://localhost:8085/trms/logout';

@Injectable()
export class CredentialsService {
    private employee: Employee = null;

    constructor(private http: HttpClient) {}

    public login(email: string, pass: string, successHandler: (Employee) => void, errorHandler: (any) => void) {
        this.logout(()=>{}, console.log);

        // note: can't set response type to not 'json' unless we remove the type parameter
        // this.http.post<any>(loginUrl, formData,{responseType: 'text'})   // doesn't work

        let formData = new FormData();
        formData.append('email', email);
        formData.append('password', pass);
        this.http.post<any>(loginUrl, formData, {withCredentials: true})
            .subscribe(
                empJson => {
                    console.log(empJson);
                    this.employee = new Employee(empJson);
                    successHandler(new Employee(empJson))
                },
                err => {
                    console.log(err);
                    if (err.status < 200 || err.status >= 300)
                        errorHandler(err);
                },
            );
    }

    public logout(successHandler: () => void, errorHandler: (err) => void) {

        this.http.post<any>(logoutUrl, new FormData(), {withCredentials: true})
            .subscribe(
                data => {
                    console.log(data);
                    this.employee = null;
                    successHandler();
                },
                err => {
                    console.log(err);
                    if (err.status < 200 || err.status >= 300)
                        errorHandler(err);
                },
            );
    }

    public getCredentials(): Employee {
        return this.employee;
    }

    public isLoggedIn() {
        return this.employee !== null;
    }
}

