import { HttpClient } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
// Import all components used by the Angular application here
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { InterpolationComponent } from './components/interpolation/interpolation.component';
import { AppRoutes } from './routing';
import { HomeComponent } from './components/home/home.component';
import { PokeApiComponent} from './components/pokeapi/pokeapi.component'

@NgModule({
  // Have angular declare all classes to be useda
  declarations: [
    AppComponent,
    NavbarComponent,
    InterpolationComponent,
    HomeComponent,
    PokeApiComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(AppRoutes)
  ],
  providers: [HttpClient],
  bootstrap: [AppComponent, NavbarComponent]
})
export class AppModule { }
