package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.RequestDAO;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import daxterix.bank.view.InputUtils;

import java.sql.SQLException;

public class RegisterCustomerPage extends Page {
    private UserDAO userDao = DAOUtils.getUserDao();

    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        String email = "";
        while(true) {
            email = InputUtils.readNonEmptyLine("email");
            if (userExists(email)) {
                System.out.println("Email taken. Please choose another email.");
                if (checkQuit())
                    return new WelcomePage();
            }
            else
                break;
        }
        String password = InputUtils.readAndConfirm(() -> InputUtils.readMasked("password"));
        System.out.printf("user %s created\n", email);

        RequestDAO reqDao = DAOUtils.getRequestDao();
        try {
            userDao.save(new User(email, password));
            reqDao.save(new UserRequest(email, UserRequest.CREATION));
            System.out.println("New master account created (locked). Please wait for an admin to unlock your new account.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new WelcomePage();
    }

    /**
     * returns true if a user with given id exists, false otherwise or if error encountered
     * @param email
     * @return
     */
    public boolean userExists(String email) {
        try {
            return userDao.select(email) != null;
        } catch (SQLException e) {
            System.out.println("SQL Exception while checking username");
        }
        return false;
    }

    @Override
    public String getTitle() {
        return "To register, fill in the fields below";
    }
}
