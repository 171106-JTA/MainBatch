package bootstrap;

import daxterix.bank.model.Admin;
import daxterix.bank.dao.AdminDAO;
import daxterix.bank.dao.DAOUtils;

public class CreateFirstAdmin {
    public static void main(String[] args) {
        Admin admin = new Admin("admin", "password");
        AdminDAO dao = DAOUtils.getAdminDao();
        dao.save(admin);
    }
}