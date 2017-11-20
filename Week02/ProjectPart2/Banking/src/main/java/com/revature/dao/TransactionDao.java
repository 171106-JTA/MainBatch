package com.revature.dao;

import java.util.ArrayList;

import com.revature.objects.Transaction;

public interface TransactionDao {

	public void newTransaction(Transaction transaction);
	public Transaction findTransaction(int transactionNber);
	public ArrayList<Transaction> listTransactionsAccount(String accountNber);
	public int deleteTransaction(int transactionNber);

}
