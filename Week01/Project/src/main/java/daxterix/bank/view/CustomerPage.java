package daxterix.bank.view;

import daxterix.bank.model.Customer;
import daxterix.bank.model.PromotionRequest;
import daxterix.bank.dao.CustomerDAO;
import daxterix.bank.dao.DAOUtils;
import daxterix.bank.dao.RequestDAO;

import java.util.Scanner;

import static daxterix.bank.view.CustomerPage.CommandEvalResult.*;

public class CustomerPage extends Page {
    private Customer customer;

    /**
     * Once logged in allows customer make deposits and withdrawals, as well as transfer
     * funds to other accounts
     *
     * @param customer
     */
    public CustomerPage(Customer customer) {
        this.customer = customer;
    }

    enum CommandEvalResult {
        INVALID_SYNTAX,
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS,
        RECIPIENT_DNE,
        DATABASE_ERROR,
        SUCCESS
    }

    /**
     * see Page._run()
     *
     * @return
     */
    @Override
    protected Page _run() {
        printAccountInfo();
        String[] cmds = {"Make a deposit", "Make a withdrawal", "Transfer funds", "View instructions", "Logout"};
        String[] codes = {"deposit [amount]", "withdraw [amount]", "transfer [amount] [recipient]", "help", "logout"};
        printCommands(cmds, codes);

        while (true) {
            String cmd = InputUtils.readLine("command");

            switch (cmd) {
                case "request promotion":
                case "rp":
                    requestPromotion();
                case "logout":
                case "exit":
                case "quit":
                    return new WelcomePage();

                default: {
                    switch (evalMoneyCommand(cmd)) {
                        case INVALID_SYNTAX:
                            System.out.println("Invalid command. Try again.");
                            break;
                        case INSUFFICIENT_FUNDS:
                            System.out.println("Insufficient funds (You are broke).");
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
                            printAccountInfo();
                            break;
                    }
                }
            }
        }
    }

    void printAccountInfo() {
        System.out.printf("Account Balance: %s\n\n\n", customer.getBalance());
    }

    boolean requestPromotion() {
        PromotionRequest req = new PromotionRequest(customer);
        RequestDAO dao = DAOUtils.getRequestDao();
        return dao.save(req);
    }

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

    CommandEvalResult withdraw(double amt) {
        if (!customer.withdraw(amt))
            return INSUFFICIENT_FUNDS;
        CustomerDAO customerDao = DAOUtils.getUnlockedCustomerDao();
        if (!customerDao.update(customer))
            return DATABASE_ERROR;

        return SUCCESS;
    }

    CommandEvalResult deposit(double amt) {
        customer.deposit(amt);

        CustomerDAO customerDao = DAOUtils.getUnlockedCustomerDao();
        if (!customerDao.update(customer))
            return DATABASE_ERROR;

        return SUCCESS;
    }

    /**
     * Transfer given amount to given user
     *
     * @param amtDest
     * @return - returns SUCCESS if transfer was completed successfully
     */
    CommandEvalResult transfer(String amtDest) {
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
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * see Page.getTitle()
     * @return
     */
    @Override
    public String getTitle() {
        return String.format("Welcome %s", customer.getUsername());
    }
}
