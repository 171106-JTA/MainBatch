package daxterix.bank.view.page;

import daxterix.bank.model.Account;
import daxterix.bank.model.User;
import daxterix.bank.view.InputUtils;
import daxterix.bank.view.OutputUtils;

import java.util.Scanner;

import static daxterix.bank.view.page.UserAccountPage.CommandEvalResult.*;

/**
 *
 * view account information, and perform transaction
 *
 */
public class UserAccountPage extends Page {
    User user;
    Account account;

    /**
     * denotes the result of evaluating a user's command
     */
    enum CommandEvalResult {
        INVALID_SYNTAX,
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS,
        RECIPIENT_DNE,
        DATABASE_ERROR,
        SUCCESS
    }

    public UserAccountPage(User user, Account account) {
        this.user = user;
        this.account = account;
        assert(user.getEmail().equals(account.getEmail()));     // todo
    }

    @Override
    protected Page _run() {

        String[] cmds = {"Make a deposit", "Make a withdrawal", "Transfer funds", "View instructions", "back to user home", "Logout"};
        String[] codes = {"deposit [amount]", "withdraw [amount]", "transfer [amount] [recipient]", "help", "home", "logout"};
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

                default: {
                    switch (evalMoneyCommand(cmd)) {
                        case INVALID_SYNTAX:
                            System.out.println("Invalid command. Enter 'help' to see possible command and their syntax.");
                            break;
                        case INSUFFICIENT_FUNDS:
                            System.out.println("Insufficient funds.");
                            break;
                        case INVALID_AMOUNT:
                            System.out.println("Invalid amount. Please enter a positive decimal.");
                            break;
                        case DATABASE_ERROR:
                            System.out.println("Something went wrong. Please try again.");
                            break;
                        case RECIPIENT_DNE:
                            System.out.println("Transfer recipient does not exist.");
                            break;
                        case SUCCESS:
                            System.out.println("Success!");
                            printAccountBalance();
                            break;
                    }
                }
            }
        }
    }

        /**
     * parse and evaluate user's commands dealing with customer's funds
     *
     * @param cmd
     * @return - SUCCESS if command is valid and is successfully executed
     */
    CommandEvalResult evalMoneyCommand(String cmd) {
        Scanner s = new Scanner(cmd);

        if (!s.hasNext())
            return INVALID_SYNTAX;
        String first = s.next();

        if (!s.hasNext())
            return INVALID_SYNTAX;
        String second = s.nextLine().trim();

        switch(first) {
            case "withdraw":
                if (!checkMoneyArgument(second)) return INVALID_AMOUNT;
                return withdraw(Double.parseDouble(second));
            case "deposit":
                if (!checkMoneyArgument(second)) return INVALID_AMOUNT;
                return deposit(Double.parseDouble(second));
            case "transfer":
                return transfer(second);
            default:
                return INVALID_SYNTAX;
        }
    }

    /**
     * withdraw given amount from customer's account
     *
     * @param amt
     * @return - SUCCESS if deposit is successful
     */
    CommandEvalResult withdraw(double amt) {
        /*
        if (!customer.withdraw(amt))
            return INSUFFICIENT_FUNDS;
        CustomerDAO customerDao = DAOUtils.getUnlockedCustomerDao();
        if (!customerDao.update(customer))
            return DATABASE_ERROR;

        return SUCCESS;
        */

        return SUCCESS; // todo: remove
    }

    /**
     * deposit given amount to customer's account
     *
     * @param amt
     * @return - SUCCESS if deposit is successful
     */
    CommandEvalResult deposit(double amt) {
        /*
        customer.deposit(amt);

        CustomerDAO customerDao = DAOUtils.getUnlockedCustomerDao();
        if (!customerDao.update(customer))
            return DATABASE_ERROR;

        return SUCCESS;
        */
        return SUCCESS; // todo: remove
    }

    /**
     * Transfer given amount to given user
     *
     * @param amtDest
     * @return - returns SUCCESS if transfer was completed successfully
     */
    CommandEvalResult transfer(String amtDest) {
        /*
        Scanner s = new Scanner(amtDest);

        String amtStr = s.next();
        if (!s.hasNext())
            return INVALID_SYNTAX;
        if (!checkMoneyArgument(amtStr))
            return INVALID_AMOUNT;
        double amt = Double.parseDouble(amtStr);

        String destUsername = s.next();

        CustomerDAO customerDao = DAOUtils.getUnlockedCustomerDao();
        if (!customerDao.doesExist(destUsername))
            return RECIPIENT_DNE;

        Customer recipient = customerDao.readById(destUsername);
        if (recipient == null)
            return DATABASE_ERROR;

        if (!customer.withdraw(amt))
            return INSUFFICIENT_FUNDS;
        if (!customerDao.update(customer))
            return DATABASE_ERROR;

        recipient.deposit(amt);
        if (!customerDao.update(recipient))
            return DATABASE_ERROR;

        return SUCCESS;
        */

        return SUCCESS; // todo: remove
    }

    /**
     * checks that the given String is indeed a number, and is > 0
     *
     * @param amt
     * @return
     */
    boolean checkMoneyArgument(String amt) {
        try {
            double amtd = Double.parseDouble(amt);
            return amtd > 0;
        }
        catch (NumberFormatException | NullPointerException e){
            return false;
        }
    }

    /**
     * print the customer's account information; the balance in this case
     */

    void printAccountBalance() {
        System.out.printf("Account Balance: %s\n\n\n", account.getBalance());
    }

    @Override
    public String getTitle() {
        return String.format("Welcome %s. Interact with your account #%d below", user.getEmail(), account.getNumber());
    }
}
