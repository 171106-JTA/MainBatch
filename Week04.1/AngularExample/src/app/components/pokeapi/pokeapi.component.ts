import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'pokeapi',
    templateUrl: '/pokeapi.component.html'
})

export class PokeApiComponent{
    public pokeId;
    public pkmn = {
        name: "",
        id: "",
        weight: "",
        sprite: ""
    }

    constructor(public http: HttpClient){
        console.log("Constructed");
    }


    /*
        Promise Vs Observable

        Angular uses Observable objects when making asynchronous calls.

        A Promise is a type of object where when a user sends data,
        we are gauranteed to get something back. In the case of sending a request,
        we get either an object representing the data recieved, or an object
        representing the error we received.
        Promises can only listen on one event at a time, on top of which, a user
        cannot cancel the event once started. IE, I cant halt a request sent to a
        server, I must wait for some kind of response to come back.

        Observables are the same as promises except they send data back as a stream
        and can provide support for 0 to many events at a time. Observable also supports
        canceling the event.
    */

    public fetchData(){
        console.log("Enter fetch");
        this.http.get('https://pokeapi.co/api/v2/pokemon/' + this.pokeId + '/').subscribe(
            data => {
                if(data == null){
                    console.log("No data");
                    return null
                }
                console.log("Got data");
                this.pkmn.name = data['name'];
                this.pkmn.id = data['id'];
                this.pkmn.weight = data['weight'];
                this.pkmn.sprite = data['sprite'];
            }
        )
    }
}