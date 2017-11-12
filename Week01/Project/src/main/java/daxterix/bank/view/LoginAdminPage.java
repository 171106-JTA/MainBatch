package daxterix.bank.view;

import daxterix.bank.model.Admin;
import daxterix.bank.presistence.AdminDAO;
import daxterix.bank.presistence.PersistUtils;

public class LoginAdminPage extends Page {

    @Override
    public Page _run() {
        Admin admin;

        while(true) {
            String username = InputUtils.readLine("username");
            String password = InputUtils.readMasked("password");

            AdminDAO dao = PersistUtils.getAdminDao();
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
    }

    @Override
    public String getTitle() {
        return "Enter Your Admin Login Credentials Below";
    }
}
