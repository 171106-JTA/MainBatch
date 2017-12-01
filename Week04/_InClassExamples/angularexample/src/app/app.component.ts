import { Component } from '@angular/core';

/* 
* Components are pieces of code that make up the web appplication for angular. 
* Each component should serve to be standalone in it's features
*/


@Component({
  selector: 'app-root', // tells us which tag to inject our html into
  templateUrl: './app.component.html', // which html file to use
  styleUrls: ['./app.component.css']
  // this is an array to tell which specific css files to use for this componentes html.
})

//Lastly in every compennet we decalre a class that can be imported that 
//represents our component.
//We create our class with 'export class classname' 
export class AppComponent {
  title = 'app';
}
