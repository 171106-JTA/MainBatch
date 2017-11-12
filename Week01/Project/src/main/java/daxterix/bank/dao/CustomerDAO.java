package daxterix.bank.dao;

import daxterix.bank.model.Customer;

public class CustomerDAO extends ObjectDAO<Customer> {
    public CustomerDAO(String saveDir) {
        super(saveDir);
    }

    @Override
    public String getId(Customer c) {
        return c.getUsername();
    }
}
