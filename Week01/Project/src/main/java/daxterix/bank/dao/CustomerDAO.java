package daxterix.bank.dao;

import daxterix.bank.model.Customer;

public class CustomerDAO extends ObjectDAO<Customer> {

    /**
     * manages CRUD operations for Customer entities, both locked and unlocked
     *
     * @param saveDir
     */
    public CustomerDAO(String saveDir) {
        super(saveDir);
    }

    /**
     * the id of a Customer is simply the username
     *
     * @param c
     * @return
     */
    @Override
    public String getId(Customer c) {
        return c.getUsername();
    }
}
