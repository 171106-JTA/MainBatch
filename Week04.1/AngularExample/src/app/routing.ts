import { Routes } from '@angular/router'; // To create routes, we will use the Routes class from angular's router library
import { InterpolationComponent } from './components/interpolation/interpolation.component'; // 
import { HomeComponent } from './components/home/home.component';
import { PokeApiComponent } from './components/pokeapi/pokeapi.component';

export const AppRoutes:Routes = [
    {
        path: 'interpolation',
        component: InterpolationComponent
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'pokeapi',
        component: PokeApiComponent
    }
]