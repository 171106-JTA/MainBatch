package com.revature.dao;

import java.util.List;

import com.revature.model.account.User;
import com.revature.model.transaction.Transaction;

public interface jBankDAO {
	public List<User> getAllUser();
	public List<Transaction> getTransactionsByUID();
	public void createUser(User user);
	public void commitQuery();
	public User grabUser(String user, String pw);
}
