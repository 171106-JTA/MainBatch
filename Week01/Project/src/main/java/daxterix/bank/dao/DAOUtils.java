package daxterix.bank.dao;

public class DAOUtils {
    /***
     * get data access object for basic CRUD operations for Admin records
     *
     * @return
     */
    public static UserDAO getUserDao() {
        return new UserDAOImpl();
    }

    public static RequestDAO2 getRequestDao() {
        return new RequestDAOImpl();
    }

    public static AccountDAO getAccountDao() {
        return new AccountDAOImpl();
    }


    /*
    public static CustomerDAO getUnlockedCustomerDao() {
        //return new CustomerDAO(unlockedCustomerPath);
        return null;
    }
    public static CustomerDAO getLockedCustomerDao() {
        //return new CustomerDAO(lockedCustomerPath);
        return null;
    }
    public static RequestDAO getRequestDao() {
        //return new RequestDAO(requestPath);
        return null;
    }
    */
}
