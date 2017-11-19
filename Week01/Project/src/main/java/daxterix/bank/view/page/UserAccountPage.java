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
    }

    @Override
    protected Page _run() {

        String[] cmds = {"View balance", "Make a deposit", "Make a withdrawal", "Transfer funds", "View instructions", "back to user home", "Logout"};
        String[] codes = {"balance", "deposit <amount>", "withdraw <amount>", "transfer <amount> <account#>", "help", "home", "logout"};
        OutputUtils.printCommands(cmds, codes);

        while (true) {
            String cmd = InputUtils.readLine("command");

            switch (cmd) {
                case "":
                    break;
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

        if (chunks.length < 2 || chunks.length > 3) {
            System.out.println("Invalid command. Enter 'help' to view available commands and their syntax.\n");
            return;
        }

        String first = chunks[0];
        String second = chunks[1];
        if (chunks.length == 2) {
            try {
                switch (first) {
                    case "withdraw":
                        handleWithdrawal(Double.parseDouble(second));
                        return;
                    case "deposit":
                        handleDeposit(Double.parseDouble(second));
                        return;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive decimal.\n");
            }
        }
        else if (first.equals("transfer")){ //(chunks.length == 3)
            String third = chunks[2];
            handleTransfer(second, third);
            return;
        }
        System.out.println("Invalid command. Enter 'help' to view available commands and their syntax.\n");
    }

    /**
     * withdraw given amount from customer's account
     *
     * @param amt
     */
    void handleWithdrawal(double amt) {
        try {
            myAccount.withdraw(amt);
            accountDao.update(myAccount);
            System.out.println("Withdrawal successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.deposit] SQL Error while depositing.\n");
        }
    }

    /**
     * deposit given amount to customer's account
     *
     * @param amt
     * @return - SUCCESS if deposit is successful
     */
    void handleDeposit(double amt) {
        try {
            myAccount.deposit(amt);
            accountDao.update(myAccount);
            System.out.println("Deposit successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.deposit] SQL Error while depositing.\n");
        }
    }

        /**
     * Transfer given amount to given account
     *
     * @param first -- represents the amount
     * @param second -- represents the destination
     */
    void handleTransfer(String first, String second) {
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

     public Page closeAccount() {
        try {
            if (!InputUtils.confirmDecision()) {
                System.out.println("Aborted.\n");
                return null;
            }

            if (myAccount.getBalance() > 0) {
                System.out.println("Cannot close account. Please withdraw or transfer funds away then try again.\n");
                return null;
            }
            else{
                accountDao.delete(myAccount.getNumber());
                System.out.println("Account closed.");
                return new CustomerPage(user);
            }
        }
        catch (SQLException e) {
            System.out.println("[UserAccountPage.closeAccount] SQL Error while closing account.\n");
        }
        return null;
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
