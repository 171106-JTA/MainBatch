import { Routes } from '@angular/router';
//To create routes, we will use the Routes class from angulars router library

//Import every component you want to inject via routing
import { InterpolationComponent } from './components/interpolation/interpolation.component'
import { HomeComponent } from './components/home/home.component';
import { PokeapiComponent } from './components/pokeapi/pokeapi.component';

//Here we create the different inject mappings for our routes
export const appRoutes: Routes = [
    {
        path: 'interpolation', //The url that will trigger the injection
        component: InterpolationComponent //the component to be injected.
    },
    {
        path: 'home', //The url that will trigger the injection
        component: HomeComponent //the component to be injected.
    },
    {
        path: 'pokeapi', //The url that will trigger the injection
        component: PokeapiComponent //the component to be injected.
    },
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    }
]

