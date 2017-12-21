import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';


import {appRoutes} from './routing';
import {AppComponent} from './app.component';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {NavComponent} from './components/nav/nav.component';
import {LoginComponent} from './components/login/login.component';
import {NewRequestComponent} from './components/new-request/new-request.component';
import {LookupsService} from './services/lookups.service';
import {RequestService} from './services/request.service';
import {CredentialsService} from './services/credentials.service';
import {RequestsComponent} from './components/requests/requests.component';
import {UserRequestsComponent} from './components/user-requests/user-requests.component';
import { RequestDetailsComponent } from './components/request-details/request-details.component';
import {DatePipe} from "@angular/common";
import { FilesComponent } from './components/files-component/files-component.component';
import {ObjectDelayDirective} from "./directives/object-delay-directive";


@NgModule({
    declarations: [
        // components
        AppComponent,
        RegisterComponent,
        HomeComponent,
        NavComponent,
        LoginComponent,
        NewRequestComponent,
        RequestsComponent,
        UserRequestsComponent,
        RequestDetailsComponent,
        FilesComponent,

        // directives
        ObjectDelayDirective
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes),
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule
    ],
    providers: [
        LookupsService,
        RequestService,
        CredentialsService,
        DatePipe
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
