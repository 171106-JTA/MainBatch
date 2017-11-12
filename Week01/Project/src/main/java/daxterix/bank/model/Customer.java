package daxterix.bank.model;

public class Customer extends User {
    private static final long serialVersionUID = -2147706395013864771L;

    private double balance;

    public Customer(String username, String password) {
        super(username, password);
        balance = 0;
    }

    public boolean withdraw(double amt) {
        if (amt > balance)
            return false;
        balance -= amt;
        return true;
    }

    public void deposit(double amt) {
        balance += amt;
    }

    public double getBalance() {
        return balance;
    }


}
