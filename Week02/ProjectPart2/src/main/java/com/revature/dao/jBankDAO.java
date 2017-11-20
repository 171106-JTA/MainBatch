package com.revature.dao;

import java.util.List;

import com.revature.model.account.User;
import com.revature.model.transaction.Transaction;

public interface jBankDAO {
	public List<User> getAllUser();
	public List<Transaction> getTransactionsByUID();
	public void createUser(User user);
	public int promoteUser(User user, String usrLvl);
	public int approveUser(String username);
	public int lockUser(User user, boolean status);
	public User grabUser(String username, String pw);
	public User grabUser(String username);
	public void commitQuery();
}
