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


@NgModule({
    declarations: [
        AppComponent,
        RegisterComponent,
        HomeComponent,
        NavComponent,
        LoginComponent,
        NewRequestComponent,
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes),
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
    providers: [
        LookupsService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
