package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;

import java.sql.SQLException;

import static daxterix.bank.view.OutputUtils.programReply;

public class LoginCustomerPage extends Page{
    /**
     * see Page._run()
     * @return
     */
    @Override
    public Page _run() {
        while(true) {
            String email = InputUtils.readLine("email");
            String password = InputUtils.readMasked("password");
            UserDAO dao = DAOUtils.getUserDao();

            User customer = null;
            try {
                customer = dao.select(email);
            }
            catch (SQLException e) {
                programReply("[LoginCustomerPage._run] SQL Exception while retrieving user");
            }
            if (customer == null)
                programReply("User does not exist. Please try again");
            else if (!customer.getPassword().equals(password))
                programReply("Invalid credentials. Please try again");
            else if (customer.isLocked())
                programReply("Account is currently locked. Please wait for an admin to unlock it.");
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
