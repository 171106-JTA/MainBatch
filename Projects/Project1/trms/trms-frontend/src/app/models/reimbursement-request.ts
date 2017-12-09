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

        this.eventStart = this.stringToDate(tjson["timeFiled"]);
        this.eventEnd = this.stringToDate(tjson["eventEnd"]);
        this.timeFiled = this.stringToDateTime(tjson["timeFiled"]);

        this.exceedsFunds = tjson["exceedsFunds"];
        this.eventType = tjson["history"];
    }

    /**
     * As name suggests
     * the + sign parses string to int
     *
     * @param {string} str
     * @returns {Date}
     */
    public stringToDate(str: string): Date {
        let chunks = str.split('-');
        return new Date(+chunks[0], +chunks[1], +chunks[2]);
    }

    /**
     * As name suggests
     * the + sign parses string to int

     *
     * @param {string} str
     * @returns {Date}
     */
    public stringToDateTime(str: string): Date {
        let chunks = str.split('-');
        return new Date(+chunks[0], +chunks[1], +chunks[2], +chunks[3], +chunks[4], +chunks[5]);
    }
}
