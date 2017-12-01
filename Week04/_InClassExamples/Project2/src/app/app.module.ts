import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

//Import all components used by the Angular application here
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { InterpolationComponent } from './components/interpolation/interpolation.component';

@NgModule({
  //Have angular declare all classes to be used
  declarations: [
    AppComponent,
    NavbarComponent,
    InterpolationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
