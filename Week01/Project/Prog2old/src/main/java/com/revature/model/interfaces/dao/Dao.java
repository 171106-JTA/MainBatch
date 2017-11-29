package com.revature.model.interfaces.dao;

import java.util.Collection;

public interface Dao {
	
	public Collection<Object> fetch(boolean atomic, String query);
	public boolean insert(boolean atomic, String query);
	public boolean update(boolean atomic, String query);
	public boolean delete(boolean atomic, String query);
	
}
