package daxterix.bank.dao;

public class DAOUtils {
    private static String url;
    private static String username;
    private static String password;

    private static String testUsername;
    private static String testPassword;

    static{
        try {
            url = System.getenv("DBUR");
            username = System.getenv("DBBANKU");
            password = System.getenv("DBBANKP");
            testUsername = System.getenv("DBTESTU");
            testPassword = System.getenv("DBTESTP");

            final String driverClass = System.getenv("DBDC");
            if (driverClass != null)
                Class.forName(driverClass);
            else {
                System.out.println("[DaoUtils] ERROR: COULD NOT RETRIEVE ENVIRONMENT AN VARIABLE; EXITTNG");
                System.exit(0);
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: COULD NOT FIND ORACLE DRIVER CLASS; EXITING");
            System.exit(0);
        }
    }

    /***
     * get data access object for basic CRUD operations for User records
     *
     * @return
     */
    public static UserDAO getUserDao() {
        return new UserDAOImpl(new ConnectionManager(username, password, url));
    }

     /***
     * get data access object for basic CRUD operations for Request records
     *
     * @return
     */
    public static RequestDAO getRequestDao() {
        return new RequestDAOImpl(new ConnectionManager(username, password, url));
    }

     /***
     * get data access object for basic CRUD operations for Account records
     *
     * @return
     */
    public static AccountDAO getAccountDao() {
        return new AccountDAOImpl(new ConnectionManager(username, password, url));
    }

     /***
     * get data access object for basic CRUD operations for User records, but for a test database
     *
     * @return
     */
    public static UserDAO getTestUserDao() {
        return new UserDAOImpl(new ConnectionManager(testUsername, testPassword, url));
    }

     /***
     * get data access object for basic CRUD operations for Account records, but for a test database
     *
     * @return
     */
    public static AccountDAO getTestAccountDao() {
        return new AccountDAOImpl(new ConnectionManager(testUsername, testPassword, url));
    }

     /***
     * get data access object for basic CRUD operations for Request records, but for a test database
     *
     * @return
     */
    public static RequestDAO getTestRequestDao() {
        return new RequestDAOImpl(new ConnectionManager(testUsername, testPassword, url));
    }
}
