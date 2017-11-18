package bootstrap;

import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.dao.DAOUtils;

import java.sql.SQLException;

public class CreateFirstAdmin {
    public static void main(String[] args) {
        User admin = new User("admin@admin.com", "password");
        admin.setAdmin(true);
        admin.setLocked(false);
        UserDAO userDao = DAOUtils.getUserDao();
        try {
            userDao.save(admin);
        } catch (SQLException e) {
            System.out.println("ERROR CREATING FIRST ADMIN");
            e.printStackTrace();
        }
    }
}
