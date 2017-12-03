import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

//Import all components used by the Angular application here
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { InterpolationComponent } from './components/interpolation/interpolation.component';
import { appRoutes } from './routing';
import { HomeComponent } from './components/home/home.component';
import { PokeapiComponent } from './components/pokeapi/pokeapi.component';


@NgModule({
  //Have angular declare all classes to be used
  declarations: [
    AppComponent,
    NavbarComponent,
    InterpolationComponent,
    HomeComponent,
    PokeapiComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }