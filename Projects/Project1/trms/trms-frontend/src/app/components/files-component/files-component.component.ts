import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ObjectDelayDirective} from "../../directives/object-delay-directive";

type FileInfo = {id: number, mimeType: string};

const filesUrl = 'http://localhost:8085/trms/files';

@Component({
    selector: 'app-files-component',
    templateUrl: './files-component.component.html',
    styleUrls: ['./files-component.component.css']
})


export class FilesComponent {
    public filesInfo: FileInfo[] = [];

    constructor(private http: HttpClient) {

        this.http.get<FileInfo[]>(filesUrl, {withCredentials: true})
            .subscribe(
                (data) => {
                    console.log(data);
                    this.filesInfo = data;
                },
                console.log
            );
    }

    public getFileUrl(info: FileInfo): string {
        return `${filesUrl}?id=${info.id}`;
    }

}
