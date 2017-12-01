import {Component} from '@angular/core'

@Component ({
    selector:'app-interpolation',
    templateUrl: './interpolation.component.html'
})

export class InterpolationComponent {
    public componentVariable = "bobbert"; 
    public someObject = {
        author: "caleb schumake", 
        age: 23
    }

    public styleObject = {
        color: 'blue',
        fontFamily: 'Comic Cans MS'

    }

    public num:number = 2; 
    public input = ""; 

    public doubleNum() {
        this.num = this.num * 2; 
    }
}

