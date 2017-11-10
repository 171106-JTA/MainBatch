package daxterix.bank.view;

import daxterix.bank.model.Customer;
import daxterix.bank.presistence.PersistUtils;
import daxterix.bank.view.Utils.InputUtils;

public class RegisterCustomerPage extends Page {
    @Override
    protected Page _run() {
        String username = InputUtils.readLine("username");
        String password = InputUtils.readAndConfirm(()->InputUtils.readMasked("password"));
        System.out.printf("user %s created\n", username);
        PersistUtils.saveUser(new Customer(username, password));
        return new ViewCustomerAccount(new Customer(username, password));
    }

    @Override
    public String getTitle() {
        return "To register, fill in the fields below";
    }
}
