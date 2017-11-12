package daxterix.bank.presistence;

import java.io.*;

public class PersistUtils {
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

    public static AdminDAO getAdminDao() {
        return new AdminDAO(adminPath);
    }

    public static CustomerDAO getUnlockedCustomerDao() {
        return new CustomerDAO(unlockedCustomerPath);
    }

    public static CustomerDAO getLockedCustomerDao() {
        return new CustomerDAO(lockedCustomerPath);
    }

    public static RequestDAO getRequestDao() {
        return new RequestDAO(requestPath);
    }

    public static boolean resourceExists(String resourcePath) {
        return (new File(resourcePath)).exists();
    }

    public static String combinePaths(String p1, String p2) {
        File file = new File(p1, p2);
        return file.getAbsolutePath();
    }
}
