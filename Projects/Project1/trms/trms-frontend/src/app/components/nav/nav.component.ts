import {Component, OnInit} from '@angular/core';
import {CredentialsService} from "../../services/credentials.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html',
    styleUrls: ['./nav.component.css']
})
export class NavComponent {

    public credentials: CredentialsService;

    constructor(private credentialsService: CredentialsService, private router: Router) {
        this.credentials = credentialsService;
    }

    public logout($event) {
        console.log("logging out");
        this.credentialsService.logout(
            () => {
                console.log("logout success");
                this.router.navigate(['/login'])
            },
            (err) => {
                console.log(err);
            }
        );
    }
}
