package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.RequestDAO;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.sql.SQLException;
import java.util.List;

public class AdminPage extends Page {
    UserDAO userDao = DAOUtils.getUserDao();
    RequestDAO reqDao = DAOUtils.getRequestDao();

    private User admin;

    /**
     * interacts with Admin users to mange Customers and their requests
     *
     * @param admin
     */
    public AdminPage(User admin) {
        this.admin = admin;
    }


    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        String[] cmds = {"View instructions", "Show pending requests", "Accept a request", "Drop a request", "Show all users", "Lock an account", "Unlock an account", "logout"};
        String[] codes = {"help", "requests", "accept <request id>", "drop <request-id>", "users", "lock <username>", "unlock <username>", "logout"};
        OutputUtils.printCommands(cmds, codes);

        while(true){
            String cmd = InputUtils.readLine("command");
            switch (cmd) {
                case "":
                    break;
                case "help":
                    OutputUtils.printCommands(cmds, codes);
                    break;
                case "users":
                    printUsers();
                    break;
                case "requests":
                    printPendingRequests();
                    break;
                case "quit":
                case "logout":
                case "exit":
                    return new WelcomePage();
                default:
                    evalCommand(cmd);
            }
        }
    }

    /**
     * print all pending requests
     */
    void printPendingRequests() {
        try {
            List<UserRequest> requests;
            requests = reqDao.selectAll();
            if (requests.isEmpty())
                System.out.println("No pending Requests\n");
            else {
                for (UserRequest req : requests)
                    System.out.println(req);
                System.out.println();
            }
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.printPendingRequests] SQL Error while retrieving pending requests.");
        }
    }


    /**
     * evaluate the approve request command where
     * approving a Creation Request is indicated by a command in the form: create [request#],
     * approving a Promotion Request is indicated by a command in the form: promote [request#],
     * and dropping any Request is indicated by a command in the form: drop [request#]
     *
     * @param cmd
     */
    void evalCommand(String cmd) {
        String[] chunks = cmd.split("\\s+");
        if (chunks.length != 2)
            System.out.println("Invalid command. Enter 'help' to see available commands and their syntax.");

        String second = chunks[1];

        switch (second) {
            case "accept":
                handleRequest(second);
            case "drop":
                dropRequest(second);
            case "lock":
                lockAccount(second);
            case "unlock":
                unlockAccount(second);
            default:
                System.out.println("Invalid command. Enter 'help' to see available commands and their syntax.");
        }
    }

    /**
     * view all customers
     */
    void printUsers() {
        try {
            List<User> customers = userDao.selectAll();
            if (customers.isEmpty())
                System.out.println("No registered users\n");
            else {
                System.out.println("Users\n");
                for (User u : customers)
                    System.out.printf("\t%-30s %-10s  %-10s\n", u.getEmail(), u.isAdmin()? "Admin" : "Customer", u.isLocked()?"Locked" : "Unlocked");
                System.out.println();
            }
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.printUsers] SQL Error while fetching all users");
        }
    }

    /**
     * lock the given user account
     * @param second
     */
    void lockAccount(String second) {
        try {
            User toBeLocked = userDao.select(second);
            if (toBeLocked == null)
                System.out.println("\nUser does not exist.\n");
            else if (toBeLocked.isAdmin())
                System.out.println("\nI lied. You do NOT have the power to lock an Admin.\n");
            else if (toBeLocked.isLocked())
                System.out.println("\nUser already locked.\n");
            else {
                toBeLocked.setLocked(true);
                userDao.update(toBeLocked);
            }
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.lockAccount] SQL Error while retrieving user for locking OR while locking user");
        }
    }

    /**
     * Unlock a previously locked account, does not work otherwise
     *
     * @param second
     */
    void unlockAccount(String second) {
        try {
            User toBeUnlocked = userDao.select(second);
            if (toBeUnlocked == null)
                System.out.println("\nUser does not exist.\n");
            else if (toBeUnlocked.isAdmin())
                System.out.println("\nI lied. You cannot lock or unlock an Admin.\n");
            else if (!toBeUnlocked.isLocked())
                System.out.println("\nUser is not locked.\n");
            else {
                toBeUnlocked.setLocked(false);
                userDao.update(toBeUnlocked);
            }
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.lockAccount] SQL Error while retrieving user for unlocking OR while unlocking user");
        }
    }

    /**
     * @param requestIdStr
     * @return
     */
    void handleRequest(String requestIdStr) {
        try {
            long requestId = Long.parseLong(requestIdStr);
            UserRequest req = reqDao.select(requestId);
            if (req == null) {
                System.out.println("Request does not exist. Press 'requests' to view pending requests and their ids.\n");
                return;
            }

            User u = userDao.select(req.getFilerEmail());   // fileEmail is an FK meaning user must exist
            if (req.getType() == UserRequest.CREATION)
                if (!u.isLocked())
                    System.out.println("User is already unlocked.");
                else
                    grantCreationRequest(req, u);
            else
                if (u.isAdmin())
                    System.out.println("User is already an admin.");
                else
                    grantPromotionRequest(req, u);
        }
        catch (SQLException e){
            System.out.println("[AdminPage.handleRequest] SQL Error while handling request command");
        }
        catch (NullPointerException | NumberFormatException e) {
            System.out.println("Please provide a valid request id. Press 'printreq' to view pending requests and their ids\n");
        }

    }

    /**
     * TODO: USE STORED PROCEDURE
     * On granting a creation request by a customer, we create the customer,
     * and persist it to the customer 'database', then delete the now-serviced
     * request.
     *
     * @param req
     * @param u
     */
    void grantCreationRequest(UserRequest req, User u) {
        try {
            reqDao.delete(req.getId());
            u.setLocked(false);
            userDao.update(u);
            System.out.println("\nUser unlocked\n");
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.grantCreationRequest] SQL Error while granting creation request");
        }
    }



    /**
     * TODO: USE STORED PROCEDURE
     * On approving a promotion request by a customer
     * promote the customer to admin, and add the new admin
     * to the admin 'database', then we delete the now-serviced request.
     *
     * @param req
     * @param u
     */
    void grantPromotionRequest(UserRequest req, User u) {
        try {
            reqDao.delete(req.getId());
            u.setAdmin(true);
            userDao.update(u);
            System.out.println("\nUser promoted to Admin\n");
        }
        catch (SQLException e) {
            System.out.println("[AdminPage.grantPromotionRequest] SQL Error while granting creation request");
        }
    }

    /**
     * drop a pending request, removing it from the request 'database'
     *
     * @param requestIdStr
     * @return
     */
    void dropRequest(String requestIdStr) {
        try {
            long requestId = Long.parseLong(requestIdStr);
            UserRequest req = reqDao.select(requestId);
            if (req == null) {
                System.out.println("Request does not exist. Press 'requests' to view pending requests and their ids.\n");
                return;
            }
            reqDao.delete(requestId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * get page title
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "You Have the Power!";
    }
}
