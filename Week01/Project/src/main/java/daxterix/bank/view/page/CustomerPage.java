package daxterix.bank.view.page;

import daxterix.bank.dao.AccountDAO;
import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.RequestDAO;
import daxterix.bank.dao.UserDAO;
import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import daxterix.bank.model.UserRequest;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.sql.SQLException;
import java.util.List;

import static daxterix.bank.view.OutputUtils.programReply;

public class CustomerPage extends Page {
    private static final double MIN_STARTING_DEPOSIT = 100;

    private User customer;
    AccountDAO accountDao = DAOUtils.getAccountDao();
    UserDAO userDao = DAOUtils.getUserDao();
    RequestDAO reqDao = DAOUtils.getRequestDao();

    /**
     * Once logged in allows customer make deposits and withdrawals, as well as transfer
     * funds to other accounts. Customers can also request promotion to an Admin account
     *
     * @param customer
     */
    public CustomerPage(User customer) {
        this.customer = customer;
    }

    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        String[] cmds = {"View accounts", "Open Account", "Go to account", "Request promotion", "View instructions", "Logout"};
        String[] codes = {"accounts", "open <starting amount>", "account <number>", "promote", "help", "logout"};
        OutputUtils.printCommands(cmds, codes);

        while (true) {
            String cmd = InputUtils.readLine("command");

            switch (cmd) {
                case "help":
                    OutputUtils.printCommands(cmds, codes);
                    break;
                case "close":
                    Page welcomePage = closeUser();
                    if (welcomePage != null)
                        return welcomePage;
                    break;
                case "accounts":
                    printAccounts();
                    break;
                case "promote":
                    handlePromoteRequest();
                    break;
                case "logout":
                case "exit":
                case "quit":
                    return new WelcomePage();
                default: {
                    Page accountPage = processAccountCommand(cmd);
                    if (accountPage != null)
                        return accountPage;
                }
            }
        }
    }

    public void printAccounts() {
        try {
            List<Account> accts = accountDao.selectForUser(customer.getEmail());
            if (accts.isEmpty()) {
                programReply("You have opened no accounts.");
                return;
            }
            programReply("Your accounts");
            for (Account a: accts)
                System.out.printf("\t%d\t$%.2f\n", a.getNumber(), a.getBalance());
            System.out.println();
        }
        catch (SQLException e) {
            programReply("[CustomerPage.printAccounts] SQL Error while retrieving user accounts.");
        }
    }

    public Page processAccountCommand(String cmd) {
        String[] chunks = cmd.split("\\s+");
        if (chunks.length != 2) {
            programReply("Invalid command. Enter 'help' to view possible commands and their syntax.");
            return null;
        }
        switch (chunks[0]) {
            case "account":
                return goToAccount(chunks[1]);
            case "open":
               openAccount(chunks[1]);
               break;
            default:
                programReply("Invalid command. Enter 'help' to view possible commands and their syntax.");
        }
        return null;
    }

    public Page openAccount(String depositStr) {
        try {
            double startingDeposit = Double.parseDouble(depositStr);
            double amtd = Double.parseDouble(depositStr);
            if (amtd < MIN_STARTING_DEPOSIT)
                System.out.printf("\nStarting deposit must be at least %s.\n\n", MIN_STARTING_DEPOSIT);
            else {
                Account newAcc = new Account(customer.getEmail(), startingDeposit);
                accountDao.save(newAcc);
                programReply("Account successfully crated!");
                printAccounts();
            }
        }
        catch (NumberFormatException | NullPointerException e){
            System.out.printf("\nStarting deposit must be a decimal of at least %s.\n\n", MIN_STARTING_DEPOSIT);
        }
        catch (SQLException e) {
            e.printStackTrace();
            programReply("[CustomerPage.openAccount] SQL Error opening new account.");
        }
        return null;
    }

    public Page closeUser() {
        try {
            if (!InputUtils.confirmDecision()) {
                programReply("Aborted.");
                return null;
            }
            if (accountDao.selectForUser(customer.getEmail()).size() != 0) {
                programReply("You still have open accounts. Please empty and close them first.");
                return null;
            }
            else{
                userDao.delete(customer.getEmail());
                programReply("User deleted.");
                return new WelcomePage();
            }
        }
        catch (SQLException e) {
            programReply("[CustomerPage.closeUser] SQL Error selecting associated accounts OR deleting user.");
        }
        return null;
    }

    public Page goToAccount(String accountStr) {
        long accountNum;
        try {
            accountNum = Long.parseLong(accountStr);
            Account selectedAcc = accountDao.select(accountNum);
            // account does not exist, or does not belong to user
            if (selectedAcc == null || !selectedAcc.getEmail().equals(customer.getEmail()))
                programReply("You do not have any account with the given number.");
            else
                return new UserAccountPage(customer, selectedAcc);
        }
        catch (NumberFormatException | NullPointerException e) {
            programReply("Invalid Syntax. Account number must be an Integer.");
        }
        catch (SQLException e) {
            programReply("[CustomerPage] SQL Error while fetching selected id.");
        }
        return null;
    }


    public void handlePromoteRequest() {
        if (customer.isAdmin()) {
            programReply("You are already an admin.");
            return;
        }
        try {
            UserRequest promotionReq = new UserRequest(customer.getEmail(), UserRequest.PROMOTION);
            boolean requestAlreadyFiled = false;
            List<UserRequest> filedRequests = reqDao.selectForUser(customer.getEmail());
            for (UserRequest req: filedRequests)
                if (req.getType() == UserRequest.PROMOTION)
                    requestAlreadyFiled = true;

            if (requestAlreadyFiled)
                programReply("Promotion request previously filed. Please wait for an admin to review request.");
            else {
                reqDao.save(promotionReq);
                programReply("Promotion request filed.");
            }
        }
        catch (SQLException e) {
            programReply("[CustomerPage.grantPromotionRequest] SQL Error checking prior requests OR saving new request.");
        }
    }

    /**
     * see Page.getTitle()
     * @return
     */
    @Override
    public String getTitle() {
        return String.format("Welcome %s", customer.getEmail());
    }
}
