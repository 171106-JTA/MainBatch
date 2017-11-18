package daxterix.bank.dao.old;

import daxterix.bank.model.old.Admin;

public class AdminDAO extends ObjectDAO<Admin> {

    /**
     * manages CRUD operations for the Admin entity
     *
     * @param saveDir
     */
    public AdminDAO(String saveDir) {
        super(saveDir);
    }

    /**
     * Like a Customer, the id for an Admin entity is the username
     *
     * @param admin
     * @return
     */
    @Override
    public String getId(Admin admin) {
        return admin.getUsername();
    }

}
