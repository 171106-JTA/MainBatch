package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.view.InputUtils;

public class LoginAdminPage extends Page {

    /**
     * authenticates user as customer, leads to a CustomerPage on successful
     * authentication
     *
     * @return
     */
    @Override
    public Page _run() {
        /*
        Admin admin;

        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");

            AdminDAO dao = DAOUtils.getAdminDao();
            admin = dao.readById(username);
            if (admin == null)
                System.out.println("Error: Admin does not exist. Please try again");
            else if (!admin.getPassword().equals(password))
                System.out.println("Error: Invalid credentials. Please try again");
            else
                return new AdminPage(admin);

            if (checkQuit())
                return new WelcomePage();
        }
        */
        return null;
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
