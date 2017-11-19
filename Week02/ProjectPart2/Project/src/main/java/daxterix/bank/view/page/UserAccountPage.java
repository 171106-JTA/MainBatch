package daxterix.bank.view.page;

import daxterix.bank.dao.AccountDAO;
import daxterix.bank.dao.DAOUtils;
import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.sql.SQLException;

import static daxterix.bank.view.OutputUtils.programReply;

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

    /**
     * see Page._run
     *
     * @return
     */
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
            programReply("Invalid command. Enter 'help' to view available commands and their syntax.");
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
                programReply("Invalid amount. Please enter a positive decimal.");
            }
        }
        else if (first.equals("transfer")){ //(chunks.length == 3)
            String third = chunks[2];
            handleTransfer(second, third);
            return;
        }
        programReply("Invalid command. Enter 'help' to view available commands and their syntax.");
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
            programReply("Withdrawal successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            programReply("[UserAccountPage.deposit] SQL Error while depositing.");
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
            programReply("Deposit successful.");
            printAccountBalance();
        }
        catch (SQLException e) {
            programReply("[UserAccountPage.deposit] SQL Error while depositing.");
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
                programReply("You can only transfer funds between your accounts.");
            else if (destAcct.getNumber() == myAccount.getNumber())
                programReply("You are attempting to transfer between the same account account.");
            else if (!myAccount.withdraw(transferAmt))
                programReply("Nice try, but you can't transfer more than you own.");
            else {
                destAcct.deposit(transferAmt);
                accountDao.update(destAcct);
                accountDao.update(myAccount);
                programReply("Transfer successful!");
                printAccountBalance();
            }
        }
        catch (SQLException e) {
            programReply("[UserAccountPage.transfer] SQL Error while transferring.");
        }
        catch (NumberFormatException e) {
            programReply("Invalid transfer command syntax. Destination must be an integer, and amount can be any positive decimal.");
        }
    }

    /**
     * closes an account, aborts if account balance is not 0
     *
     * @return
     */
     public Page closeAccount() {
        try {
            if (!InputUtils.confirmDecision()) {
                programReply("Aborted.");
                return null;
            }

            if (myAccount.getBalance() > 0) {
                programReply("Cannot close account. Please withdraw or transfer funds away then try again.");
                return null;
            }
            else{
                accountDao.delete(myAccount.getNumber());
                programReply("Account closed.");
                return new CustomerPage(user);
            }
        }
        catch (SQLException e) {
            programReply("[UserAccountPage.closeAccount] SQL Error while closing account.");
        }
        return null;
    }


    /**
     * print the customer's account information; the balance in this case
     */

    void printAccountBalance() {
        System.out.printf("\nAccount Balance: %s\n\n", myAccount.getBalance());
    }

    @Override
    public String getTitle() {
        return String.format("Welcome %s. Interact with your account (id %d) below", user.getEmail(), myAccount.getNumber());
    }
}
