package daxterix.bank;

import daxterix.bank.view.Page;
import daxterix.bank.view.WelcomePage;

public class App {

    public static void main(String[] args) {
        Page page = new WelcomePage();
        page.run();
    }
}
