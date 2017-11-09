package daxterix.bank.view;

import daxterix.bank.view.Utils.InputUtils;

public class LoginPage extends Page{
    @Override
    public Page _run() {
        String username = InputUtils.readLine("username");
        String password = InputUtils.readMasked("password");

        System.out.printf("\nUser %s created\n\n\n", username);
        return null;
    }

    @Override
    public String getTitle() {
        return "Enter Your Desired Credentials Below";
    }
}
