import {Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import {NewRequestComponent} from './components/new-request/new-request.component';
import {UserRequestsComponent} from './components/user-requests/user-requests.component';

export const appRoutes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'new-request',
        component: NewRequestComponent
    },
    {
        path: 'requests',
        component: UserRequestsComponent
    },
    {
        path: '**',
        component: HomeComponent
    }
];
