package daxterix.bank.model;

public class Admin extends User {
    private static final long serialVersionUID = -8172994959930890687L;

    /**
     * represents Admin accounts who have the power to accept or deny
     * customer creation and promotion (to Admin) requests, as well as
     * the ability to lock and unlock customers from their accounts
     *
     * @param username
     * @param password
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * creates an Admin given a customer, represents the promotion
     * of a customer to an Admin
     *
     * @param customer
     */
    public Admin(Customer customer) {
        super(customer.getUsername(), customer.getPassword());
    }
}
