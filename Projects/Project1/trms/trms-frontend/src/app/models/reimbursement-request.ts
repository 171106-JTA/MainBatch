import {Time} from '@angular/common';

export class ReimbursementRequest {
    public id: number;
    public filerEmail: string;
    public status: string;
    public cost: number;
    public funding: number;
    public description: string;

    public passed: boolean;
    public isUrgent: boolean;
    public timeFiled: Time;
    public eventStart: Date;
    public eventEnd: Date;

    public exceedsFunds: boolean;
    public eventType: string;

    public files: File[];
    public history: History[];
}
