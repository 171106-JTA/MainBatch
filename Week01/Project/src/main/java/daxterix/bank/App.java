package daxterix.bank;

import daxterix.bank.view.page.Page;
import daxterix.bank.view.page.WelcomePage;

public class App {

    public static void main(String[] args) {
        Page page = new WelcomePage();
        page.run();
    }
}
