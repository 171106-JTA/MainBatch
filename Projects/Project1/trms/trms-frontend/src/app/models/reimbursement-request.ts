import {Utils} from "./utils";

export class ReimbursementRequest {
    public id: number;
    public filerEmail: string;
    public status: string;
    public cost: number;
    public funding: number;
    public description: string;

    public passed: boolean;
    public isUrgent: boolean;
    public timeFiled: Date;
    public eventStart: Date;
    public eventEnd: Date;

    public exceedsFunds: boolean;
    public eventType: string;

    public files: File[];
    public history: History[];


    /**
     * constructs the new request based on server json response
     *
     * @param tjson
     */
    constructor(tjson: any) {
        this.id = tjson["id"];
        this.filerEmail = tjson["filerEmail"];
        this.status = tjson["status"];
        this.cost = tjson["cost"];
        this.funding = tjson["funding"];
        this.description = tjson["description"];

        this.passed = tjson["passed"];
        this.isUrgent = tjson["isUrgent"];

        this.timeFiled = Utils.jsonToDateTime(tjson["timeFiled"]);
        this.eventEnd = Utils.jsonToDate(tjson["eventEnd"]);
        this.eventStart = Utils.jsonToDate(tjson["eventStart"]);

        this.exceedsFunds = tjson["exceedsFunds"];
        this.eventType = tjson["history"];
    }
}
