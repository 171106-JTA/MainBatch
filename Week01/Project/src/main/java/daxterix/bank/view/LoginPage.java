package daxterix.bank.view;

import daxterix.bank.model.Customer;
import daxterix.bank.presistence.CustomerDAO;
import daxterix.bank.presistence.PersistUtils;
import daxterix.bank.view.Utils.InputUtils;

public class LoginPage extends Page{
    @Override
    public Page _run() {
        Customer customer;

        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");

            CustomerDAO dao = PersistUtils.getCustomerDao();
            customer = dao.readById(username);
            if (customer == null)
                System.out.println("Error: User does not exist. Please try again");
            else if (!customer.getPassword().equals(password))
                System.out.println("Error: Invalid credentials. Please try again");
            else
                return new ViewCustomerAccount(customer);

            if (shouldQuit(InputUtils.readLine("'quit' to quit or anything else to try-again")))
                return new WelcomePage();
        }
    }

    boolean shouldQuit(String cmd) {
        switch(cmd) {
            case "q":
                return true;
            case "quit":
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getTitle() {
        return "Enter Your Login Credentials Below";
    }
}
