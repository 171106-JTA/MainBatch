package com.revature.model.daoImpl;

import java.util.Collection;
import java.util.Set;

import com.revature.model.DBProperties;

public class UserDaoImpl extends DaoImpl {
	protected static String fetch = DBProperties.SQL_DIR + UserDaoImpl.class.getSimpleName() + DBProperties.FETCH;
	protected static String insert = DBProperties.SQL_DIR + UserDaoImpl.class.getSimpleName() + DBProperties.INSERT;
	protected static String update = DBProperties.SQL_DIR + UserDaoImpl.class.getSimpleName() + DBProperties.UPDATE;
	protected static String delete = DBProperties.SQL_DIR + UserDaoImpl.class.getSimpleName() + DBProperties.DELETE;

	@Override
	public Collection<Object> fetch(boolean atomic, String query) {
		
		return null;
	}

	@Override
	public void setObject(Object object, int prot) {
	}

	@Override
	public boolean insert(boolean atomic, String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(boolean atomic, String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(boolean atomic, String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Object> getObject(Set<Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}
}
