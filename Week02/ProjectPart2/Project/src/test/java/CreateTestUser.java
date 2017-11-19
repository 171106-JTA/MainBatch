import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;

import java.sql.SQLException;

public class CreateTestUser {
    public static void main(String[] args) throws SQLException {
        UserDAO user = DAOUtils.getUserDao();
        String email = "test", pass = "password";
        User u = new User(email, pass);
        u.setLocked(false);
        user.save(u);
    }
}
