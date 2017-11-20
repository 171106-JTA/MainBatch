package com.revature.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

import com.revature.model.DBProperties;
import com.revature.model.Properties;
import com.revature.model.account.Account;
import com.revature.model.interfaces.dao.Dao.Protocol;
import com.revature.model.interfaces.product.Pool;

public class JDBC {

	// JDBC singleton instance
	private static JDBC jdbc;

	// basic sql functions
	String[] commands = { "", "", "", "" };
	String[] files = { DBProperties.FETCH, DBProperties.INSERT, DBProperties.UPDATE, DBProperties.DELETE };

	// precompile a pool of Database Connections
	private static Pool<Connection> pool;

	private JDBC() {

		// initialize a pool of connections
		pool = new ConnectionPool<Connection>();
		pool.createPool(Properties.POOL_SIZE);

		// extract sql files for querying
		for (int i = 0; i < commands.length; i++) {
			try (Stream<String> fileStream = Files.lines(Paths.get(files[i]))) {
				commands[i] += fileStream.map(line -> line + "\n");
			} catch (IOException e) {
				// Throw fatal error later
				e.printStackTrace();
				return;
			}
		}
	}

	public static JDBC getInstance() {
		if (jdbc == null)
			jdbc = new JDBC();
		return jdbc;
	}

	public synchronized static Object query(List<String> queries, Protocol prot, boolean... atomic) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		List<String> cols;
		List<List<String>> rows;
		int[] values;

		try {

			// borrow connection
			if ((conn = pool.borrowObject()) == null && atomic != null && atomic[0] == true) {
				return null;
			}

			if (prot == Protocol.FETCH) {
				ps = conn.prepareStatement(queries.get(0));
				rs = ps.executeQuery();
			} else {
				ps = conn.prepareStatement("");
				for (String s : queries) {
					ps.addBatch(s);
				}
				return  ps.executeBatch();
			}

			// execute
			rsmd = rs.getMetaData();
			cols = new ArrayList<String>(rsmd.getColumnCount());
			rows = new ArrayList<List<String>>();
			while (rs.next()) {
				for (int i = 1; i < rsmd.getColumnCount(); i++) {
					cols.add(rs.getString(i));
				}
				rows.add(cols);
			}

			return rows;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	private class ConnectionPool<T> implements Pool<T> {
		Set<Connection> locked = Collections.synchronizedSet(new HashSet<Connection>());
		Set<Connection> open = Collections.synchronizedSet(new HashSet<Connection>());
		private Semaphore sem;

		@Override
		public void createPool(int size) {
			sem = new Semaphore(size, true);
			for (int i = 0; i < size;) {
				try {
					if (open.add(DriverManager.getConnection(DBProperties.URL)))
						i++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public synchronized T borrowObject(boolean... atomic) {
			try {
				if (sem.availablePermits() == 0 && atomic != null && atomic[0] == true)
					return null;
				sem.acquire();
				if (open.isEmpty())
					open.add(DriverManager.getConnection(DBProperties.URL));
				return (T) open.iterator().next();
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
				return null;
			} finally {
				sem.release();
			}
		}

		@Override
		public synchronized boolean returnObject(Connection c) {
			if (!locked.remove(c))
				return false;
			sem.release();
			return open.add(c);

		}

		@Override
		public boolean freePool() {
			try {
				sem.acquire();
				if (!locked.isEmpty())
					return false;
				for (Connection c : open) {
					c.close();
					open.remove(c);
				}
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sem.release();
			return open.isEmpty();
		}
	}

	public void queryMap(Map<String, Account> map) {
		Connection c = pool.borrowObject(false);
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement("SELECT * FROM USERS NATURAL JOIN USER_TYPE"
					+ " NATURAL JOIN SSN NATURAL JOIN ACCOUNT_VARIANCE NATURAL JOIN "
					+ "NATURAL JOIN STATE NATURAL JOIN STATUS NATURAL JOIN ");
		} catch(SQLException e) {
			
		}
		
	}
}
