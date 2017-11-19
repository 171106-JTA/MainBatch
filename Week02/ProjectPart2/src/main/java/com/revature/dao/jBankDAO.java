package com.revature.dao;

import java.util.List;

import com.revature.model.account.User;
import com.revature.model.transaction.Transaction;

public interface jBankDAO {
	public List<User> getAllUser();
	public List<Transaction> getTransactionsByUID();
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUserByUID(int uid);
}
