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
        String[] codes = {"accounts", "open <deposit amount>", "account <number>", "promote", "help", "logout"};
        OutputUtils.printCommands(cmds, codes);

        while (true) {
            String cmd = InputUtils.readLine("command");

            switch (cmd) {
                case "help":
                    OutputUtils.printCommands(cmds, codes);
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
                System.out.println("\nYou have opened no accounts.\n");
                return;
            }
            System.out.println("\nYour accounts:");
            for (Account a: accts)
                System.out.printf("\t%d\t$%.2f\n", a.getNumber(), a.getBalance());
            System.out.println();
        }
        catch (SQLException e) {
            System.out.println("[CustomerPage.printAccounts] SQL Error while retrieving user accounts.");
        }
    }

    public Page processAccountCommand(String cmd) {
        String[] chunks = cmd.split("\\s+");
        if (chunks.length != 2) {
            System.out.println("Invalid command/syntax. Enter 'help' to view possible commands and their syntax.");
            return null;
        }
        switch (chunks[0]) {
            case "account":
                return goToAccount(chunks[1]);
            case "open":
               openAccount(chunks[1]);
               break;
            default:
                System.out.println("Invalid command/syntax. Enter 'help' to view possible commands and their syntax.");
        }
        return null;
    }

    public Page openAccount(String depositStr) {
        try {
            double startingDeposit = Double.parseDouble(depositStr);
            double amtd = Double.parseDouble(depositStr);
            if (amtd < MIN_STARTING_DEPOSIT)
                System.out.printf("Starting deposit must be at least %s.\n", MIN_STARTING_DEPOSIT);
            else {
                Account newAcc = new Account(customer.getEmail(), startingDeposit);
                accountDao.save(newAcc);
                System.out.println("Account successfully crated!");
                printAccounts();
            }
        }
        catch (NumberFormatException | NullPointerException e){
            System.out.printf("Starting deposit must be a decimal of at least %s.\n", MIN_STARTING_DEPOSIT);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[CustomerPage.openAccount] SQL Error opening new account.");
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
                System.out.println("You do not have any account with the given number.");
            else
                return new UserAccountPage(customer, selectedAcc);
        }
        catch (NumberFormatException | NullPointerException e) {
            System.out.println("Invalid Syntax. Account number must be an Integer");
        }
        catch (SQLException e) {
            System.out.println("[CustomerPage] SQL Error while fetching selected id");
        }
        return null;
    }


    public void handlePromoteRequest() {
        if (customer.isAdmin()) {
            System.out.println("You are already an admin.\n");
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
                System.out.println("Promotion request previously filed. Please wait for an admin to review request.");
            else {
                reqDao.save(promotionReq);
                System.out.println("Request filed successfully!");
            }
        }
        catch (SQLException e) {
            System.out.println("[CustomerPage.grantPromotionRequest] SQL Error checking prior requests OR saving new request");
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
