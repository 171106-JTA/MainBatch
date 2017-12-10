export class Employee {
    email: string;
    rank: string;
    address: string = null;
    city: string = null;
    state: string = null;
    zip: number = null;
    firstname: string;
    lastname: string;
    birthday: Date = null;
    phone: number = null;
    availableFunds: number;
    supervisorEmail: string;
    department: string;

    constructor(empJson: any) {
        this.email = empJson.email;
        this.rank = empJson.rank;
        if (empJson.address != null) {
            this.address = empJson.address.address;
            this.city = empJson.address.city;
            this.state = empJson.address.state;
            this.zip = empJson.address.zip;
        }
        if (empJson.birthday != null) {
            let jsonDate: any = empJson.birthday;
            this.birthday = new Date();
            this.birthday.setFullYear(jsonDate.year, jsonDate.month, jsonDate.day);
            this.birthday.setHours(jsonDate.hour, jsonDate.minute, jsonDate.second);
        }
        this.firstname = empJson.firstname;
        this.lastname = empJson.lastname;
        this.phone = empJson.phone;
        this.availableFunds = empJson.availableFunds;
        this.department = empJson.department;
        this.supervisorEmail = empJson.supervisorEmail;
    }
}

