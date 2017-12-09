import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LookupsService} from '../../services/lookups.service';
import {HttpClient} from '@angular/common/http';
import {CredentialsService} from '../../services/credentials.service';


const loginUrl = 'http://localhost:8085/trms/login';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    loginForm: FormGroup = new FormGroup({
        email: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
    });

    constructor(private lookups: LookupsService, private http: HttpClient, private credentialsService: CredentialsService) {
    }

    private email: string;
    private loginFailed = false;

    private login($event) {
        let formData: FormData = new FormData();
        for (let formControlKey in this.loginForm.controls)
            formData.append(formControlKey, this.loginForm.get(formControlKey).value);
        this.email = this.loginForm.get("email").value;

        // can't set response type to not 'json' unless we remove the type parameter
        // this.http.post<any>(loginUrl, formData,{responseType: 'text'})   // doesn't work
        console.log("logging in");
        this.http.post(loginUrl, formData,{withCredentials: true, responseType: 'text'})
            .subscribe(
                data => {
                    this.loginFailed = false;
                    this.credentialsService.login(this.email);      // set the email for the credentials service
                    console.log(data);
                },
                err => {
                    this.loginFailed = true;
                },
                () => {
                }
            );
    }
}

