import { Component } from '@angular/core';

@Component({
  selector: 'app-root', //tells us which tag to inject our html into
  templateUrl: './app.component.html', //which html file to use
  styleUrls: ['./app.component.css'] 
  //this is an array that we can use to tell which specifc css files to use for this component's html
})

/* */
export class AppComponent {
  title = 'app';
}
