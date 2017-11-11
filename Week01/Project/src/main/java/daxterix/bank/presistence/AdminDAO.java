package daxterix.bank.presistence;

import daxterix.bank.model.Admin;

public class AdminDAO extends ObjectDAO<Admin> {
    public AdminDAO(String saveDir) {
        super(saveDir);
    }

    @Override
    public String getId(Admin admin) {
        return admin.getUsername();
    }
}
