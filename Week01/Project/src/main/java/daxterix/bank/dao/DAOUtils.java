package daxterix.bank.dao;

import java.io.*;

public class DAOUtils {
    static String appPersistHome = "Desktop\\userInfo";
    static String unlockedCustomerPath = "Desktop\\userInfo\\customer\\unlocked";
    static String lockedCustomerPath = "Desktop\\userInfo\\customer\\locked";
    static String adminPath = "Desktop\\userInfo\\admin";
    static String requestPath = "Desktop\\userInfo\\request";


    // this static block serves to initialize save file paths which is tied to user.home,
    // and to check that program has readObject write access to the paths
    static {
        String sysHomeDir = System.getProperty("user.home");
        adminPath = (new File(sysHomeDir, adminPath)).getAbsolutePath();
        unlockedCustomerPath = (new File(sysHomeDir, unlockedCustomerPath)).getAbsolutePath();
        lockedCustomerPath = (new File(sysHomeDir, lockedCustomerPath)).getAbsolutePath();
        requestPath = (new File(sysHomeDir, requestPath)).getAbsolutePath();
        appPersistHome = (new File(sysHomeDir, appPersistHome)).getAbsolutePath();

        File appPersistHomeFile = new File(appPersistHome);
        if (!(appPersistHomeFile.exists() || appPersistHomeFile.mkdirs())){
            System.err.println("fatal error: could not create resource directories. application exiting.");
            System.exit(-1);
        }
    }

    /***
     * get data access object for basic CRUD operations for Admin records
     *
     * @return
     */
    public static AdminDAO getAdminDao() {
        return new AdminDAO(adminPath);
    }

    /**
     * get data access object for basic CRUD operations for unlocked Customer records
     *
     * @return
     */
    public static CustomerDAO getUnlockedCustomerDao() {
        return new CustomerDAO(unlockedCustomerPath);
    }

    /**
     * get data access object for basic CRUD operations for locked Customer records
     *
     * @return
     */
    public static CustomerDAO getLockedCustomerDao() {
        return new CustomerDAO(lockedCustomerPath);
    }

    /**
     * get data access object for basic CRUD operations for CustomerRequest records
     *
     * @return
     */
    public static RequestDAO getRequestDao() {
        return new RequestDAO(requestPath);
    }
}
