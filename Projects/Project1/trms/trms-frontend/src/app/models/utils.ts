export class Utils {
    public static jsonToDateTime(dateJson: any): Date {
        if (dateJson === null || dateJson === undefined)
            return null;
        let res = new Date();
        res.setFullYear(dateJson.year, dateJson.month, dateJson.date);
        res.setHours(dateJson.hour, dateJson.minute, dateJson.second);
        return res;
    }

    public static jsonToDate (input: any): Date {
        if (input === null || input === undefined)
            return null;
        let res = new Date();
        res.setFullYear(input.year, input.month, input.date);
        res.setHours(0, 0, 0);
        return res;
    }
}
