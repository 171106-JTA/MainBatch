import { Component } from '@angular/core';

@Component({
    selector: 'app-pipe',
    templateUrl: './pipes.component.html'
})

export class PipesComponent{
    public names = [ 
        "bobbert",
        "bobberta",
        "ryan",
        "ryann",
        "Jack",
        "Jill",
        23,
        false,
        null,
        undefined

    ]
}