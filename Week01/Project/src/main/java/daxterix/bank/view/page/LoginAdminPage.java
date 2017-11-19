package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;

import java.sql.SQLException;

public class LoginAdminPage extends Page {

    /**
     * authenticates user as an admin  leads to an AdminPage on successful
     * authentication
     *
     * @return
     */
    @Override
    public Page _run() {
        User admin = null;

        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");

            UserDAO dao = DAOUtils.getUserDao();
            try {
                admin = dao.select(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (admin == null || !admin.getPassword().equals(password))
                System.out.println("Invalid credentials.");
            else if (admin.isLocked())
                System.out.println("Nice try, but your account is locked.");
            else if (!admin.isAdmin())
                System.out.println("Nice try, but you're not an admin.");
            else
                return new AdminPage(admin);

            if (InputUtils.checkQuit())
                return new WelcomePage();
        }
    }

    /**
     * see Page.getTitle()
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "Enter Your Admin Login Credentials Below";
    }
}
