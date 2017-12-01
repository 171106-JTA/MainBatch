import { Component } from '@angular/core';

/*
  Components are pieces of code that make up the web application for angular.
  Each component should serve to be standalone in it's features.
*/

@Component({
  selector: 'app-root', // Tells us which tag to inject our html into
  templateUrl: './app.component.html', // Which html file to use
  styleUrls: ['./app.component.css'] // An array that we can use to tell which specific css files to use for this component's html
})
// In every component we declare a class that can be imported taht represents our component
// We create our class with 'export class className'
export class AppComponent {
  title = 'app';
}
