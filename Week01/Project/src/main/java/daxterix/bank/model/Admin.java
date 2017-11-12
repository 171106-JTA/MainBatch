package daxterix.bank.model;

public class Admin extends User {
    private static final long serialVersionUID = -8172994959930890687L;

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(Customer customer) {
        super(customer.getUsername(), customer.getPassword());
    }
}
