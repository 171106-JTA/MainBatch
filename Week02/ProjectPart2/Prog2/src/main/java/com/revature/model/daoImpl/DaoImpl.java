package com.revature.model.daoImpl;

import java.util.*;

import com.revature.model.account.Account;
import com.revature.model.interfaces.dao.Dao;
import com.revature.util.JDBC;

public class DaoImpl implements Dao {
	
	public Map<String, Account> getAccounts(Map<String, Account> map) {
		JDBC.getInstance().queryMap(map);
	}

	@Override
	public HashMap<String, Account> getAccounts(HashMap<String, Account> list) {
		// TODO Auto-generated method stub
		return null;
	}
}