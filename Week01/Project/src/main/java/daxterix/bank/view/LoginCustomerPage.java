package daxterix.bank.view;

import daxterix.bank.model.old.Customer;
import daxterix.bank.dao.old.CustomerDAO;
import daxterix.bank.dao.DAOUtils;

public class LoginCustomerPage extends Page{
    /**
     * see Page._run()
     * @return
     */
    @Override
    public Page _run() {
        Customer customer;

        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");

            CustomerDAO dao = DAOUtils.getUnlockedCustomerDao();
            customer = dao.readById(username);
            if (customer == null)
                System.out.println("Error: User does not exist. Please try again");
            else if (!customer.getPassword().equals(password))
                System.out.println("Error: Invalid credentials. Please try again");
            else
                return new CustomerPage(customer);

            if (checkQuit())
                return new WelcomePage();
        }
    }

    @Override
    public String getTitle() {
        return "Enter Your Customer Login Credentials Below";
    }
}
