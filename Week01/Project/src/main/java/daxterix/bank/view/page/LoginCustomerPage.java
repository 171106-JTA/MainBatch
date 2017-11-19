package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;

import java.sql.SQLException;

public class LoginCustomerPage extends Page{
    /**
     * see Page._run()
     * @return
     */
    @Override
    public Page _run() {
        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");
            UserDAO dao = DAOUtils.getUserDao();

            User customer = null;
            try {
                customer = dao.select(username);
            }
            catch (SQLException e) {
                System.out.println("[LoginCustomerPage._run] SQL Exception while retrieving user\n");
            }
            if (customer == null)
                System.out.println("User does not exist. Please try again\n");
            else if (!customer.getPassword().equals(password))
                System.out.println("Invalid credentials. Please try again\n");
            else if (customer.isLocked())
                System.out.println("Account is currently locked. Please wait for an admin to unlock it.\n");
            else
                return new CustomerPage(customer);

            if (InputUtils.checkQuit())
                return new WelcomePage();
        }
    }

    @Override
    public String getTitle() {
        return "Enter Your Customer Login Credentials Below";
    }
}
