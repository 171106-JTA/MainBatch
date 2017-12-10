import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LookupsService} from '../../services/lookups.service';
import {HttpClient} from '@angular/common/http';
import {CredentialsService} from '../../services/credentials.service';
import {Router} from '@angular/router';
import {Employee} from "../../models/employee";


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

    constructor(private router: Router, private lookups: LookupsService,
                private http: HttpClient, private credentialsService: CredentialsService) {
    }

    private loginFailed = false;

    private login($event) {
        console.log("logging in");
        let email = this.loginForm.get("email").value;
        let password = this.loginForm.get("password").value;

        return this.credentialsService.login(email, password,
            (emp: Employee) => {
                console.log("login success");
                console.log(emp);
                this.loginFailed = false;
                this.router.navigate(['/requests']);
            },
            (err) => {
                console.warn(err);
                this.loginFailed = true;
            }
        );
    }

}

