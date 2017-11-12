package daxterix.bank.view;

import daxterix.bank.model.*;
import daxterix.bank.presistence.AdminDAO;
import daxterix.bank.presistence.CustomerDAO;
import daxterix.bank.presistence.PersistUtils;
import daxterix.bank.presistence.RequestDAO;
import static daxterix.bank.view.AdminPage.CommandEvalResult.*;

import java.util.List;
import java.util.Scanner;

public class AdminPage extends Page {
    private Admin admin;

    public AdminPage(Admin admin) {
        this.admin = admin;
    }

    enum CommandEvalResult {
        SUCCESS,
        INVALID_SYNTAX,
        REQUEST_DNE,
        DATABASE_ERROR,
        NO_SUCH_UNLOCKED_USER,
        NO_SUCH_LOCKED_USER,
    }

    @Override
    protected Page _run() {
        String[] cmds = {"View instructions", "Show pending requests", "Grant a creation request", "Grant a promotion request", "Drop a request", "Show locked customers", "Lock an account", "Unlock an account", "logout"};
        String[] codes = {"help", "printreq", "create [request id]", "promote [request id]", "drop [request-id]", "printlocked", "lock [username]", "unlock [username]", "locked", "logout"};
        printCommands(cmds, codes);

        while(true){
            String cmd = InputUtils.readLine("command");
            switch (cmd) {
                case "help":
                    printCommands(cmds, codes);
                    break;
                case "printlocked":
                    printLockedCustomers();
                    break;
                case "printreq":
                    printPendingRequests();
                    break;
                case "quit":
                case "logout":
                case "exit":
                    return new WelcomePage();
                default:
                    switch (evalCommand(cmd)) {
                        case INVALID_SYNTAX:
                            System.out.println("Invalid command. Try again.");
                            break;
                        case DATABASE_ERROR:
                            System.out.println("Something went wrong. Request id may be invalid. Try again.");
                            break;
                        case REQUEST_DNE:
                            System.out.println("Request with specified id does not exist. Try again.");
                            break;
                        case NO_SUCH_LOCKED_USER:
                            System.out.println("Locked customer with id does not exist.");
                            break;
                        case NO_SUCH_UNLOCKED_USER:
                            System.out.println("Unlocked customer with id does not exist.");
                            break;
                        case SUCCESS:
                            System.out.println("Success!");
                    }
            }
        }
    }

    /**
     * print all pending requests
     *
     * @return
     */
    boolean printPendingRequests() {
        RequestDAO dao = PersistUtils.getRequestDao();
        List<UserRequest> requests =  dao.readAll();
        if (requests == null)
            return false;
        if (requests.isEmpty())
            System.out.println("No pending Requests\n");
        else {
            for (UserRequest req : requests)
                System.out.println(req);
            System.out.println();
        }
        return true;
    }


    /**
     * evaluate the approve request command where
     * approving a Creation Request is indicated by a command in the form: create [request#],
     * approving a Promotion Request is indicated by a command in the form: promote [request#],
     * and dropping any Request is indicated by a command in the form: drop [request#]
     *
     * @param cmd
     * @return
     */
    CommandEvalResult evalCommand(String cmd) {
        Scanner s = new Scanner(cmd);

        if (!s.hasNext())
            return INVALID_SYNTAX;
        String first = s.next();

        if (!s.hasNext())
            return INVALID_SYNTAX;
        String second = s.nextLine().trim();

        switch (first) {
            case "promote":
                return approvePromoteRequest(second);
            case "create":
                return approveCreateRequest(second);
            case "drop":
                return dropRequest(second);
            case "lock":
                return lockAccount(second);
            case "unlock":
                return unlockAccount(second);
            default:
                return INVALID_SYNTAX;
        }
    }

    CommandEvalResult printLockedCustomers() {
        CustomerDAO lockedCustomerDAO = PersistUtils.getLockedCustomerDao();
        List<Customer> lockedCustomers = lockedCustomerDAO.readAll();
        if (lockedCustomers == null)
            return DATABASE_ERROR;
        else if (lockedCustomers.isEmpty())
            System.out.println("No locked customers\n");
        else
            for (Customer c: lockedCustomers)
                System.out.println(c.getUsername());
        return SUCCESS;
    }

    private CommandEvalResult lockAccount(String second) {
        CustomerDAO unlockedDao = PersistUtils.getUnlockedCustomerDao();

        Customer customer = unlockedDao.readById(second);
        if (customer == null)
            return NO_SUCH_UNLOCKED_USER;

        CustomerDAO lockedDao = PersistUtils.getLockedCustomerDao();
        if (!(lockedDao.save(customer) && unlockedDao.deleteById(customer.getUsername())))
            return DATABASE_ERROR;

        return SUCCESS;
    }

    private CommandEvalResult unlockAccount(String second) {
        CustomerDAO lockedDao = PersistUtils.getLockedCustomerDao();

        Customer customer = lockedDao.readById(second);
        if (customer == null)
            return NO_SUCH_LOCKED_USER;

        CustomerDAO unlockedDao = PersistUtils.getUnlockedCustomerDao();
        if (!(unlockedDao.save(customer) && lockedDao.deleteById(customer.getUsername())))
            return DATABASE_ERROR;

        return SUCCESS;
    }

    /**
     * On granting a creation request by a customer, we create the customer,
     * and persist it to the customer 'database', then delete the now-serviced
     * request.
     *
     * Transaction support would be nice here.
     *
     * @param requestId
     * @return
     */
    CommandEvalResult approveCreateRequest (String requestId) {
        RequestDAO requestDao = PersistUtils.getRequestDao();
        CustomerDAO customerDao = PersistUtils.getUnlockedCustomerDao();

        CreationRequest req = (CreationRequest) requestDao.readById(requestId);
        if (req == null)
            return REQUEST_DNE;

        Customer customer = (Customer)req.getRequester();
        if (requestDao.deleteById(requestId) && customerDao.save(customer))
            return SUCCESS;
        else
            return DATABASE_ERROR;
    }

    /**
     * On approving a promotion request by a customer
     * promote the customer to admin, and add the new admin
     * to the admin 'database', then we delete the now-serviced request.
     *
     * Transaction support would be nice here.
     *
     * @param requestId
     * @return
     */
    CommandEvalResult approvePromoteRequest (String requestId) {
        RequestDAO requestDao = PersistUtils.getRequestDao();
        AdminDAO adminDao = PersistUtils.getAdminDao();

        UserRequest req = requestDao.readById(requestId);
        if (req == null)
            return REQUEST_DNE;

        if (!(req instanceof PromotionRequest))
            return INVALID_SYNTAX;

        Customer customer = (Customer) req.getRequester();
        Admin promotedCustomer = new Admin(customer);
        if ((adminDao.save(promotedCustomer) && requestDao.deleteById(requestId)))
            return SUCCESS;
        else
            return DATABASE_ERROR;
    }

    /**
     * drop a pending request, removing it from the request 'database'
     *
     * @param requestId
     * @return
     */
    CommandEvalResult dropRequest(String requestId) {
        RequestDAO requestDao = PersistUtils.getRequestDao();
        if (!requestDao.doesExist(requestId))
            return REQUEST_DNE;
        if (!requestDao.deleteById(requestId))
            return DATABASE_ERROR;
        return SUCCESS;
    }

    @Override
    public String getTitle() {
        return "You Have the Power!";
    }
}
