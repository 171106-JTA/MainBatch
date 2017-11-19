package daxterix.bank.view.page;

import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.RequestDAO;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static daxterix.bank.view.OutputUtils.programReply;

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
        String[] codes = {"help", "requests", "accept <request id>", "drop <request-id>", "users", "lock <email>", "unlock <email>", "logout"};
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
                programReply("No pending Requests");
            else {
                programReply("Pending Requests");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a MM/dd/yyyy");
                for (UserRequest req : requests) {
                    System.out.printf(
                            "\t(id %5s)\t%-20s\tby %-25s on %s\n",
                            ""+req.getId(),
                            req.getType() == UserRequest.CREATION? "Unlock Request" : "Promotion Request",
                            req.getFilerEmail(), formatter.format(req.getFileDate())
                    );
                }
                System.out.println();
            }
        }
        catch (SQLException e) {
            programReply("[AdminPage.printPendingRequests] SQL Error while retrieving pending requests.");
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
        if (chunks.length != 2) {
            programReply("Invalid command. Enter 'help' to see available commands and their syntax.");
            return;
        }

        String first = chunks[0], second = chunks[1];
        switch (first) {
            case "grant":
            case "fulfill":
            case "accept":
                handleRequest(second);
                break;
            case "drop":
                dropRequest(second);
                break;
            case "lock":
                lockAccount(second);
                break;
            case "unlock":
                unlockAccount(second);
                break;
            default:
                programReply("Invalid command. Enter 'help' to see available commands and their syntax.");
        }
    }

    /**
     * view all customers
     */
    void printUsers() {
        try {
            List<User> customers = userDao.selectAll();
            if (customers.isEmpty())
                programReply("No registered users");
            else {
                programReply("Users");
                for (User u : customers)
                    System.out.printf("\t%-30s %-10s  %-10s\n", u.getEmail(), u.isAdmin()? "Admin" : "Customer", u.isLocked()?"Locked" : "Unlocked");
            }
            System.out.println();
        }
        catch (SQLException e) {
            programReply("[AdminPage.printUsers] SQL Error while fetching all users");
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
                programReply("User does not exist.");
            else if (toBeLocked.isAdmin())
                programReply("I lied. You do NOT have the power to lock an Admin.");
            else if (toBeLocked.isLocked())
                programReply("User already locked.");
            else {
                toBeLocked.setLocked(true);
                userDao.update(toBeLocked);
                System.out.printf("\nUser %s locked.\n\n", toBeLocked.getEmail());
            }
        }
        catch (SQLException e) {
            programReply("[AdminPage.lockAccount] SQL Error while retrieving user for locking OR while locking user.");
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
                programReply("User does not exist.");
            else if (toBeUnlocked.isAdmin())
                programReply("I lied. You cannot lock or unlock an Admin.");
            else if (!toBeUnlocked.isLocked())
                programReply("User is not locked.");
            else {
                toBeUnlocked.setLocked(false);
                userDao.update(toBeUnlocked);
                System.out.printf("\nUser %s unlocked.\n\n", toBeUnlocked.getEmail());
            }
        }
        catch (SQLException e) {
            programReply("[AdminPage.lockAccount] SQL Error while retrieving user for unlocking OR while unlocking user.");
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
                programReply("Request does not exist. Press 'requests' to view pending requests and their ids.");
                return;
            }

            User u = userDao.select(req.getFilerEmail());   // fileEmail is an FK meaning user must exist
            if (req.getType() == UserRequest.CREATION)
                if (!u.isLocked())
                    programReply("User is already unlocked.");
                else
                    grantCreationRequest(req, u);
            else
                if (u.isAdmin())
                    programReply("User is already an admin.");
                else
                    grantPromotionRequest(req, u);
        }
        catch (SQLException e){
            programReply("[AdminPage.handleRequest] SQL Error while handling request command.");
        }
        catch (NullPointerException | NumberFormatException e) {
            programReply("Please provide a valid request id. Press 'printreq' to view pending requests and their ids.");
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
            programReply("User unlocked");
        }
        catch (SQLException e) {
            programReply("[AdminPage.grantCreationRequest] SQL Error while granting creation request.");
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
            programReply("User promoted to Admin");
        }
        catch (SQLException e) {
            programReply("[AdminPage.grantPromotionRequest] SQL Error while granting creation request");
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
                programReply("Request does not exist. Press 'requests' to view pending requests and their ids.");
                return;
            }
            reqDao.delete(requestId);
            System.out.printf("\nRequest %d dropped.\n\n", requestId);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            programReply("Invalid id. Enter 'requests' to view pending requests and their ids.");
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
