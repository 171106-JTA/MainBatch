import { Routes } from "@angular/router"; 
import { InterpolationComponent } from "./components/interpolation/interpolation.component"
import { HomeComponent } from './components/home/home.component'
import { PokeapiComponent } from './components/pokeapi/pokeapi.component'

//to create routes we will use the routes class from angulars router library
//import every component you want to inject via routing
// here e create the different injection mappings for our routes
//
export const appRoutes: Routes = [
    {
        path: 'interpolation', // url that will trigger the injection
        component: InterpolationComponent // the component to be injected
    },
    {
        path: 'home',
        component: HomeComponent
    }, 
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'pokeapi',
        component: PokeapiComponent
    }
]

