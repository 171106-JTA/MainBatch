import {Injectable} from '@angular/core';

@Injectable()
export class CredentialsService {
    private email: string = null;

    public login(email: string) {
        return this.email = email;
    }
    public logout() {
        this.email = null;
    }

    public isLoggedIn() {
        return this.email === null;
    }
}

