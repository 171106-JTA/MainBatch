import daxterix.bank.view.LoginPage;
import daxterix.bank.view.WelcomePage;

public class App {

    public static void main(String[] args) {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.run();
        LoginPage loginPage = new LoginPage();
        loginPage.run();
        //InputUtils.readMasked("password");
    }
}
