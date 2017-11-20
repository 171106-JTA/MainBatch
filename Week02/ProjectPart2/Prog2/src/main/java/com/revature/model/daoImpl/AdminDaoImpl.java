package com.revature.model.daoImpl;

import com.revature.model.interfaces.dao.AdminDao;
import com.revature.model.interfaces.dao.Dao;

public class AdminDaoImpl<T> implements AdminDao<T> {

	@Override
	public void createDao() {
		 new AdminDaoImpl();
	}

	@Override
	public T readFrom(Commands comm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeTo(Commands comm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T readFromAtomic(Commands comm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeToAtomic(Commands comm) {
		// TODO Auto-generated method stub
		return false;
	}
}
