package com.revature.model.interfaces.dao;

import java.util.HashMap;

import com.revature.model.account.Account;

import com.revature.util.JDBC;

public interface Dao {
	public static void initJDBC() {
		JDBC.getInstance();
	}
	public HashMap<String, Account> getAccounts(HashMap<String, Account> list);
}
//
// public T getT();
//
//// public T readFrom();
////
//// public void writeTo(Protocol comm);
////
//// public T readFromAtomic();
////
//// public boolean writeToAtomic(Protocol comm);
////
//// public static enum Protocol {
//// FETCH, INSERT, UPDATE, DELETE
//// }
// }
