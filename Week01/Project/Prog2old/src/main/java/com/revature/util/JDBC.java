package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import com.revature.model.DBProperties;
import com.revature.model.daoImpl.*;
import com.revature.model.interfaces.product.Pool;

public class JDBC {
	// JDBC singleton instance
	private static JDBC jdbc;

	// pre=compile a thread for reading SQL files
	private Thread sqlReaderThread = new SQLReaderThread();

	// pre-compile a pool of Database Connections
	private Pool<Connection> pool;

	private JDBC() {
		sqlReaderThread.start();
		pool = new ConnectionPool<Connection>(DBProperties.POOL_SIZE);
	}

	public static synchronized JDBC getinstance() {
		if (jdbc == null)
			jdbc = new JDBC();
		return jdbc;
	}

	public List<Object> queryDB(String query) {
		return null;
	}

	private class ConnectionPool<T> implements Pool<T> {
		Set<T> locked = Collections.synchronizedSet(new HashSet<T>());
		Set<T> open = Collections.synchronizedSet(new HashSet<T>());

		public ConnectionPool(int size) {

		}

		@SuppressWarnings("unchecked")
		@Override
		public void createPool(int size) {
			for (int i = 0; i < size;) {
				try {
					if (open.add((T) DriverManager.getConnection(DBProperties.URL)))
						i++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public synchronized T borrowObject() {
			if (open.isEmpty())
				try {
					open.add((T) DriverManager.getConnection(DBProperties.URL));
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			return open.iterator().next();
		}

		@SuppressWarnings("unchecked")
		@Override
		public synchronized boolean returnObject(Connection c) {
			if (!locked.remove(c))
				return false;
			return open.add((T) c);

		}

		@Override
		public boolean freePool() {
			if (!locked.isEmpty())
				return false;
			for (T c : open) {
				try {
					((Connection) c).close();
					open.remove(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return open.isEmpty();
		}
	}

	public Collection<Object> read(String fetch) {

		return null;
	}

	public boolean write(String fetch) {
		// TODO Auto-generated method stub
		return false;
	}

	private class SQLReaderThread extends Thread {

		@Override
		public void run() {
			DaoImpl.readSQLFiles(UserDaoImpl.class);
			DaoImpl.readSQLFiles(AdminDaoImpl.class);
			DaoImpl.readSQLFiles(RequestDaoImpl.class);
			DaoImpl.readSQLFiles(TransactionDaoImpl.class);
			DaoImpl.readSQLFiles(LoanDaoImpl.class);
		}
	}
}
