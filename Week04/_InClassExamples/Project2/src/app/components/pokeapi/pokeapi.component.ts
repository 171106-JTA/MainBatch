import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http'; //Bring in our httpClient library
@Component({
    selector: 'app-poke',
    templateUrl: './pokeapi.component.html'
})

export class PokeapiComponent{
    public pokeId;
    public pkmn = {
        name: "",
        id: "",
        weight: "",
        sprite: ""
    }

    //Constructor will execute upon instantiation of the class.
    //We can use a constructor to inject objects from other classes
    //into our class for use.
    //In this case, we will inject the HttpClient class
    constructor(private http: HttpClient){

    }
    /*
        Promise Vs Observable
        
        Angular uses Observable objects when making asynchronous calls.

        A Promise is a type of object where when a user sends data,
        we are gaurnateed to get something back. In the case of sending a request,
        we either get back an object representing the data recieved, or an object
        representing the error we recieved.
        Promises can only listen on one event at a time, on top of which, a user
        cannot cancel the event once started. IE, I cant halt a request sent to 
        a server, I must wait for some kind of response to come back.

        Observables are the same as promises except with more features. Observables
        send the data back, essentially, as a stream. With that, Observables can
        provide support for 0-many events at a time. Observable also supports
        canceling the event.
    */
    public fetchData(){
        this.http.get('https://pokeapi.co/api/v2/pokemon/' + this.pokeId + '/').subscribe(
            data => { //data represents the object of a successful REST request
                this.pkmn.name = data["name"];
                this.pkmn.id = data["id"];
                this.pkmn.weight = data["weight"];
                this.pkmn.sprite = data["sprites"]["front_default"];
            },
            err => {
                console.log(err);
            }
        )
    }

}