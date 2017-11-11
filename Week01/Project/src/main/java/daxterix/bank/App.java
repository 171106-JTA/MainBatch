package daxterix.bank;

import daxterix.bank.view.LoginPage;
import daxterix.bank.view.Page;
import daxterix.bank.view.RegisterCustomerPage;

public class App {

    public static void main(String[] args) {
        Page page = new LoginPage();
        page.run();

        /*
        RegisterCustomerPage registration = new RegisterCustomerPage();
        registration.run();
        */
    }
}
