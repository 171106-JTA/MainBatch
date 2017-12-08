import {Time} from '@angular/common';

export class RequestHistory {
    public id: number;
    public requestId: number;
    public emailFileId: number;
    public approverEmail: string;
    public time: Time;
    public postStatus: string;
    public reason: string;
}


