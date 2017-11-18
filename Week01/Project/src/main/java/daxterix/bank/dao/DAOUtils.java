package daxterix.bank.dao;

import daxterix.bank.dao.old.AdminDAO;
import daxterix.bank.dao.old.CustomerDAO;
import daxterix.bank.dao.old.RequestDAO;

public class DAOUtils {
    /***
     * get data access object for basic CRUD operations for Admin records
     *
     * @return
     */
    public static AdminDAO getAdminDao() {
        //return new AdminDAO(adminPath);
        return null;
    }

    /**
     * get data access object for basic CRUD operations for unlocked Customer records
     *
     * @return
     */
    public static CustomerDAO getUnlockedCustomerDao() {
        //return new CustomerDAO(unlockedCustomerPath);
        return null;
    }

    /**
     * get data access object for basic CRUD operations for locked Customer records
     *
     * @return
     */
    public static CustomerDAO getLockedCustomerDao() {
        //return new CustomerDAO(lockedCustomerPath);
        return null;
    }

    /**
     * get data access object for basic CRUD operations for CustomerRequest records
     *
     * @return
     */
    public static RequestDAO getRequestDao() {
        //return new RequestDAO(requestPath);
        return null;
    }
}
