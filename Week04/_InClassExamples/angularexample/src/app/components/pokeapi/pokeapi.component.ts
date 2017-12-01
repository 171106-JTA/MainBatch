import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 

@Component({
    selector: 'app-poke',
    templateUrl: './pokeapi.component.html'
})

export class PokeapiComponent {
    public pokeid
    public pkmn = {
        name:"",
        id:"",
        weight:"",
        sprite:""
    }

    // will execute upon istainiation of the class
    // can use constructor to inject objects from other classes 
    //into our class for use
    //In this case, we will inject the HttpClient class
    constructor(private http: HttpClient){

    }

    // promise vs obersable, angular uses obserable objects when making anyschronous calls
    // a promise is a type of object where a user sends data, we are
    // guranteed to get something back. In the case of sending a request
    // we either get back an objecy representing the data received
    // or an object representing the error we reieved
    // promises can only liste on one evenet at a time, on top of which, a user
    //cannot cancel the event once started. IE, I cant halt a request sent to 
    // aa server, I must wait for somoe kindof response to come back
    // Observables are the same as promises exceto with more feautres.
    //obseravbles send the data back, essentially, as a stream. with that,
    //obserables can provide support for 0-many events ata time.
    // observable also supports canceling the event

    public fetchData(){
        
        this.http.get('https://pokeapi.co/api/v2/pokemon/' + this.pkmn.id + '/').subscribe(
            data => { // represents the object of a successfull REST request
                this.pkmn.name = data["name"];
                this.pkmn.id = data["id"];
                this.pkmn.weight = data["weight"];
                this.pkmn.sprite= data["sprite"]["front_default"]; 

            }, 
            err => {
                console.log(err); 
            }
        )
    }
}