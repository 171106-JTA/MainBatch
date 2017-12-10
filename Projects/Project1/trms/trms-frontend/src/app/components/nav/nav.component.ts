import {Component, OnInit} from '@angular/core';
import {CredentialsService} from "../../services/credentials.service";

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html',
    styleUrls: ['./nav.component.css']
})
export class NavComponent {

    constructor(private credentialsService: CredentialsService) {
    }

    public logout($event) {
        console.log("logging out");
        this.credentialsService.logout(
            () => {console.log("logout success");},
            (err) => {console.log(err);}
        );
    }
}
