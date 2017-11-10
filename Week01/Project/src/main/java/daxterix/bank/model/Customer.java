package daxterix.bank.model;

public class Customer extends User {
    private static final long serialVersionUID = -2147706395013864771L;

    private double balance;

    public Customer(String username, String password) {
        super(username, password);
    }

    public void deposit(double amt) {
    }

    public void withdraw(double amt) {
    }

}
