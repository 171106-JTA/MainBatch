package com.revature.model.interfaces.dao;

import com.revature.model.account.AdminAccount;

public class AdminDao<T> implements Dao<T> {

	@Override
	public void createDao() {
		 new AdminDao();
	}
}
