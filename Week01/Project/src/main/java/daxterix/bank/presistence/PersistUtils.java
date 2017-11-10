package daxterix.bank.presistence;

import daxterix.bank.model.User;

import java.io.*;

public class PersistUtils {
    private static String userInfoDir;

    static {    // do i even need this?
        String workingDir = System.getProperty("user.dir");
        System.err.printf("%s ---\n", workingDir);

        File userInfoFile = new File(workingDir, "userInfo");
        userInfoDir = userInfoFile.getAbsolutePath();

        if (!userInfoFile.exists() && !userInfoFile.mkdirs()){
            System.err.println("fatal error: could not create resource directory. application exiting.");
            System.exit(-1);
        }
    }

    public static boolean saveUser(User user) {
        System.out.println("something");
        String saveLoc = getUserSaveLoc(user.getUsername(), user.getPassword());
        System.out.println("something");
        File saveLocFile = new File(saveLoc);
        System.out.println("something");

        File parentFile = (saveLocFile.getParentFile());
        System.out.println("something");
        System.out.println(parentFile);
        System.out.println("something");

        saveLocFile.getParentFile().mkdirs();
        System.out.println("something");

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUserSaveLoc(user.getUsername(), user.getPassword())))) {
            oos.writeObject(user);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User fetchUser (String username, String password) {
        try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(getUserSaveLoc(username, password)))) {
            return (User)oos.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean resourceExists(String resourcePath) {
        return (new File(resourcePath)).exists();
    }

    public static boolean doesUsernameExist(String username) {
        String userPath = combinePaths(userInfoDir, username);
        return resourceExists(userPath);
    }

    public static String getUserSaveLoc(String username, String password) {
        String userPassCombined = String.format("%s%s%s", username, File.separator, password);
        return combinePaths(userInfoDir, userPassCombined);
    }

    public static String combinePaths(String p1, String p2) {
        File file = new File(p1, p2);
        return file.getAbsolutePath();
    }

}
