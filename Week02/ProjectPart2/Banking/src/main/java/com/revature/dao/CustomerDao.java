package com.revature.dao;

import java.util.List;

import com.revature.objects.Bank_customer;;

public interface CustomerDao {

	public void newCustomer(Bank_customer cust);
	public Bank_customer findById(int id);
	public void validateCustomer(int id);
	public void promoteCustomer(int id);
	public List<Bank_customer> listOfAllCustomer();
	public int deleteCustomer(Integer id);

}
