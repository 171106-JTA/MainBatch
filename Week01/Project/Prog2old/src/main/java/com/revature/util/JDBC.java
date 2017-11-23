package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import com.revature.model.Properties;
import com.revature.model.dao.Dao;
import com.revature.model.interfaces.product.Pool;

public class JDBC {
	// JDBC singleton instance
	private JDBC jdbc;
	
	// precompile a pool of Database Connections
	private Pool<Connection> pool = new ConnectionPool();
	
	private JDBC(){
	}
	
	public  synchronized JDBC getinstance() {
		if(jdbc == null)
			jdbc = new JDBC();
		return jdbc;
	}
	
	private class ConnectionPool implements Pool<Connection> {
		Set<Connection> locked = Collections.synchronizedSet(new HashSet<Connection>());
		Set<Connection> open = Collections.synchronizedSet(new HashSet<Connection>());

		@Override
		public void createPool(int size) {
			for(int i = 0; i < size;) {
				try {
					if(open.add(DriverManager.getConnection(Properties.DB_URL)))
						i++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public synchronized Connection borrowObject() {
			if (open.isEmpty())
				try {
					open.add(DriverManager.getConnection(Properties.DB_URL));
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			return open.iterator().next();
		}

		@Override
		public synchronized boolean returnObject(Connection c) {
			if (!locked.remove(c))
				return false;
			return open.add(c);

		}

		@Override
		public boolean freePool() {
			if(!locked.isEmpty())
				return false;
			for (Connection c : open) {
				try {
					c.close();
					open.remove(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return open.isEmpty();
		}
	}
}
