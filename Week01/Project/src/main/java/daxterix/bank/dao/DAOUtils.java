package daxterix.bank.dao;

import java.io.*;

public class DAOUtils {
    public static String appPersistHome = "Desktop\\userInfo";
    public static String unlockedCustomerPath = "Desktop\\userInfo\\customer\\unlocked";
    public static String lockedCustomerPath = "Desktop\\userInfo\\customer\\locked";
    public static String adminPath = "Desktop\\userInfo\\admin";
    public static String requestPath = "Desktop\\userInfo\\request";


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

    /**
     * check if a file path exists
     *
     * @param resourcePath
     * @return
     */
    public static boolean resourceExists(String resourcePath) {
        return (new File(resourcePath)).exists();
    }

    /**
     * combine two file paths
     *
     * @param p1
     * @param p2
     * @return
     */
    public static String combinePaths(String p1, String p2) {
        File file = new File(p1, p2);
        return file.getAbsolutePath();
    }
}
