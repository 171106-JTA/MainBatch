package daxterix.bank.model;

public class Customer extends User {
    private static final long serialVersionUID = -2147706395013864771L;

    private double balance;

    /**
     * the 'main' account holder. models a normal customer who uses the bank app
     * to make deposits, withdrawals, and transfers
     *
     * @param username
     * @param password
     */
    public Customer(String username, String password) {
        super(username, password);
        balance = 0;
    }

    /**
     * withdraw given amount from account balance if there are
     * sufficient funds to make the withdrawal
     *
     * @param amt
     * @return - true if amt <= balance
     */
    public boolean withdraw(double amt) {
        if (amt > balance)
            return false;
        balance -= amt;
        return true;
    }

    /**
     * deposit given amount to account
     *
     * @param amt
     */
    public void deposit(double amt) {
        balance += amt;
    }

    /**
     * get account balance
     *
     * @return
     */
    public double getBalance() {
        return balance;
    }


}
