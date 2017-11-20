package com.revature.dao;

import com.revature.beans.Customer;

public interface CustomerDao {

	public void createCust(Customer acct);
	public Customer selectCustById(Integer id);
	public void deleteCust (String username);
	public void displayCust();
	
}