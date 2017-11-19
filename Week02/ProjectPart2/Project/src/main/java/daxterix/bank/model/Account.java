package daxterix.bank.model;

public class Account {
    private long number = -1;
    private String email;
    private double balance;

    public Account() {
    }

    public Account(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * deposit given amount to account balance, amount must be >= 0
     *
     * @param amt
     * @return
     */
    public boolean deposit(double amt) {
        if (amt < 0)
            return false;
        balance += amt;
        return true;
    }

    /**
     * withdraw given amount from account balance, amount must be <= balance and >= 0
     *
     * @param amt
     * @return
     */
    public boolean withdraw(double amt) {
        if (amt < 0 || amt > balance)
            return false;
        balance -= amt;
        return true;
    }


    /**
     * compare two Accounts, overlook ids if one of them has an id of -1
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (number != account.number && !(number == -1 || account.number == -1)) return false;
        if (Double.compare(account.balance, balance) != 0) return false;
        return email.equals(account.email);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (number ^ (number >>> 32));
        result = 31 * result + email.hashCode();
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
