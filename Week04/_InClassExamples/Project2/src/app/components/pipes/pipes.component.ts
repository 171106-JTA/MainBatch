import { Component } from '@angular/core';
import { CustomPipe } from '../../pipes/custom.pipe';


@Component({
    selector: 'app-pipe',
    templateUrl: './pipes.component.html'
})

export class PipesComponent{
alterWords = "";

    public names = [ 
        "bobbert",
        "bobberta",
        "ryanasd",
        "ryann",
        "Jack",
        "Jill"

    ]
}