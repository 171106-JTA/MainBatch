package bootstrap;

import daxterix.bank.model.Admin;
import daxterix.bank.presistence.AdminDAO;
import daxterix.bank.presistence.PersistUtils;

public class CreateFirstAdmin {
    public static void main(String[] args) {
        Admin admin = new Admin("admin", "password");
        AdminDAO dao = PersistUtils.getAdminDao();
        dao.save(admin);
    }
}
