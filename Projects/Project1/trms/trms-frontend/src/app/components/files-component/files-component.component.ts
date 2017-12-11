import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

const filesUrl = 'http://localhost:8085/trms/files';

@Component({
    selector: 'app-files-component',
    templateUrl: './files-component.component.html',
    styleUrls: ['./files-component.component.css']
})

export class FilesComponent {
    public ids: number[] = [];

    constructor(private http: HttpClient) {

        this.http.get<number[]>(filesUrl, {withCredentials: true})
            .subscribe(
                (data) => {
                    console.log(data);
                    this.ids = data;
                },
                console.log
            );
    }

}
