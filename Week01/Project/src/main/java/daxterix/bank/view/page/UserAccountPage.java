package daxterix.bank.view.page;

import daxterix.bank.dao.AccountDAO;
import daxterix.bank.dao.DAOUtils;
import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.sql.SQLException;

/**
 *
 * view account information, and perform transaction
 *
 */
public class UserAccountPage extends Page {
    private User user;
    private Account myAccount;
    private AccountDAO accountDao = DAOUtils.getAccountDao();


    public UserAccountPage(User user, Account myAccount) {
        this.user = user;
        this.myAccount = myAccount;
        assert(user.getEmail().equals(myAccount.getEmail()));     // todo
    }

    @Override
    protected Page _run() {

        String[] cmds = {"View balance", "Make a deposit", "Make a withdrawal", "Transfer funds", "View instructions", "back to user home", "Logout"};
        String[] codes = {"balance", "deposit <amount>", "withdraw <amount>", "transfer <amount> <account#>", "help", "home", "logout"};
        OutputUtils.printCommands(cmds, codes);

        while (true) {
            String cmd = InputUtils.readLine("command");

            switch (cmd) {
                case "home":
                    return new CustomerPage(user);
                case "logout":
                case "exit":
                case "quit":
                    return new WelcomePage();
                case "balance":
                    printAccountBalance();
                    break;
                case "close":
                    Page nextPage = closeAccount();
                    if (nextPage != null)
                        return nextPage;
                    break;
                default: {
                    evalTxCommand(cmd);
                }
            }
        }
    }

    /**
     * parse and evaluate user's commands dealing with the Account
     *
     * @param cmd
     */
    void evalTxCommand(String cmd) {
        String[] chunks = cmd.split("\\s+");
        if (chunks.length != 2)
            System.out.println("Invalid command/syntax. Enter 'help' to view available commands and their syntax.");

        String first = chunks[0];
        String second = chunks[1];

        try {
            switch (first) {
                case "withdraw":
                    withdraw(Double.parseDouble(second));
                case "deposit":
                    deposit(Double.parseDouble(second));
                case "transfer":
                    transfer(second);
                default:
                    System.out.println("Invalid command. Enter 'help' to view available commands and their syntax.");
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a positive number.");
        }
    }

    /**
     * withdraw given amount from customer's account
     *
     * @param amt
     */
    void withdraw(double amt) {
        try {
            myAccount.withdraw(amt);
            accountDao.update(myAccount);
            System.out.println("Withdrawal successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.deposit] SQL Error while depositing.");
        }
    }

    /**
     * deposit given amount to customer's account
     *
     * @param amt
     * @return - SUCCESS if deposit is successful
     */
    void deposit(double amt) {
        try {
            myAccount.deposit(amt);
            accountDao.update(myAccount);
            System.out.println("Deposit successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.deposit] SQL Error while depositing.");
        }
    }

     public Page closeAccount() {
        try {
            if (!InputUtils.confirmDecision())
                return null;

            if (myAccount.getBalance() > 0) {
                System.out.println("Cannot close account. Please withdraw or transfer funds away then try again.");
                return null;
            }
            else{
                accountDao.delete(myAccount.getNumber());
                System.out.println("Account closed.");
                return new WelcomePage();
            }
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.closeAccount] SQL Error while closing account.");
        }
        return null;
    }

    /**
     * Transfer given amount to given user
     *
     * @param amtDest
     */
    void transfer(String amtDest) {
        String[] chunks = amtDest.split("\\s+");
        if (chunks.length != 2)
            System.out.println("Invalid command/syntax. Enter 'help' to view available commands and their syntax.");

        String first = chunks[0];
        String second = chunks[1];

        try {
            double transferAmt = Double.parseDouble(first);
            long destAccountNum = Long.parseLong(second);

            Account destAcct = accountDao.select(destAccountNum);
            if (destAcct == null || !destAcct.getEmail().equals(user.getEmail()))
                System.out.println("You can only transfer funds between your accounts.\n");
            else if (destAcct.getNumber() == myAccount.getNumber())
                System.out.println("You are attempting to transfer between the same account account.\n");
            else if (!myAccount.withdraw(transferAmt))
                System.out.println("Nice try, but you can't transfer more than you own.\n");
            else {
                destAcct.deposit(transferAmt);
                accountDao.update(destAcct);
                accountDao.update(myAccount);
                System.out.println("Transfer successful!");
                printAccountBalance();
            }
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.transfer] SQL Error while transferring.");
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid transfer command syntax. Destination must be an integer, and amount can be any positive decimal.\n");
        }
    }

    /**
     * print the customer's account information; the balance in this case
     */

    void printAccountBalance() {
        System.out.printf("Account Balance: %s\n\n", myAccount.getBalance());
    }

    @Override
    public String getTitle() {
        return String.format("Welcome %s. Interact with your account #%d below", user.getEmail(), myAccount.getNumber());
    }
}
