import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { RouterModule } from '@angular/router'


import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component'; 
import { InterpolationComponent } from './components/interpolation/interpolation.component'; 
import { appRoutes } from './routing'; 
import { HomeComponent } from './components/home/home.component'; 
import { PokeapiComponent } from './components/pokeapi/pokeapi.component'; 
import { HttpClientModule } from '@angular/common/http'; 

@NgModule({
  declarations: [
    // have angular declare all classes to be used
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

