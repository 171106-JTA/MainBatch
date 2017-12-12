package com.revature.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.Semaphore;

import com.revature.model.Constants;

public class JDBC implements Pool<Connection> {

	// Connection pool open/locked collections
	Set<Connection> locked = Collections.synchronizedSet(new HashSet<Connection>());
	Set<Connection> open = Collections.synchronizedSet(new HashSet<Connection>());
	public static final String URL = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:ORCL";
	public static final String USER = "sean";
	public static final String PASS = "sean1234";
	private static Thread connPool;

	// Connection pool semaphore
	private Semaphore sem;

	// JDBC singleton instance
	private static JDBC jdbc;

	private JDBC() {

		// initialize a pool of connections
		// pool.createPool(DBProperties.POOL_SIZE);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JDBC getInstance() {
		if (jdbc == null) {
			jdbc = new JDBC();
			connPool = jdbc.new ConnectionPoolThread();
			connPool.setDaemon(true);
			connPool.start();
		}
		return jdbc;
	}

	public void destroyJDBC() {
		freePool();
		jdbc = null;
	}

	@Override
	public void createPool(int size) {
		sem = new Semaphore(size, true);
		for (int i = 0; i < size;) {
			try {
				open.add(DriverManager.getConnection(URL, USER, PASS));
				i++;
			} catch (SQLRecoverableException e1) {
				List<Connection> remove = new LinkedList<Connection>();
				try {
					for (Connection c : open) {
						if (c.isClosed()) {
							remove.add(c);
						}
					}

					for (Connection c : remove) {
						open.remove(c);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized Connection borrowObject(boolean atomic) {
		try {
			if (sem.availablePermits() == 0 && atomic == true)
				return null;
			sem.acquire();
			if (open.isEmpty())
				open.add(DriverManager.getConnection(URL));
			return open.iterator().next();
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			sem.release();
		}
	}

	@Override
	public synchronized boolean returnObject(Connection c, boolean atomic) {
		if (!locked.remove(c))
			return false;
		sem.release();
		return open.add((Connection) c);

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

	// if (blob && i == blobIdx) {
	// file = new File(path);
	// fis = new FileInputStream(file);
	// cs.setBinaryStream(blobIdx, fis, file.length());

	public boolean executeProcedure(String procStr, boolean atomic, String[] values) throws IOException {
		Connection c = null;
		try {
			if ((c = preprocess(c, atomic)) == null)
				return false;

			CallableStatement cs = c.prepareCall(procStr);
			for (int i = 1; i <= values.length; i++) {
				cs.setString(i, values[i - 1]);
			}

			cs.executeQuery();
			postprocess(c);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean executeProcedureBlob(String procStr, boolean atomic, String[] values, int blobIdx, String path)
			throws IOException {
		FileInputStream fis = null;
		Connection c = null;
		File file = null;
		try {
			if ((c = preprocess(c, atomic)) == null)
				return false;

			CallableStatement cs = c.prepareCall(procStr);
			for (int i = 1; i <= values.length; i++) {
				if (i == blobIdx) {
					file = new File(path);
					fis = new FileInputStream(file);
					cs.setBinaryStream(blobIdx, fis, file.length());
				} else {
					cs.setString(i, values[i - 1]);
				}

			}
			cs.executeQuery();

			postprocess(c);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	public Collection<Map<String, String>> fetch(String queryStr, String[] keys, boolean atomic) {
		Collection<Map<String, String>> obs = new LinkedHashSet<Map<String, String>>();
		Map<String, String> map = null;
		int size = 0;
		String key;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSetMetaData rsmd = null;
		Connection c = null;

		try {
			if ((c = preprocess(c, atomic)) == null)
				return null;

			ps = c.prepareStatement(queryStr);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			size = rsmd.getColumnCount();
			while (rs.next()) {
				map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= size; i++) {
					key = keys == null ? rsmd.getColumnName(i) : keys[i - 1];
					map.put(key, rs.getString(i));
				}
				obs.add(map);
			}

			rs.close();
			ps.close();
			postprocess(c);

		} catch (SQLException e) {
			try {
				c.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return obs;
	}

	public Collection<Map<String, Object>> fetchBlob(String queryStr, String[] keys, boolean atomic, String blobCol) {
		Collection<Map<String, Object>> obs = new LinkedHashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		int size;
		String key;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Connection c = null;

		try {
			if ((c = preprocess(c, atomic)) == null)
				return null;

			PreparedStatement ps = c.prepareStatement(queryStr);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			size = rsmd.getColumnCount();
			while (rs.next()) {
				map = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= size; i++) {
					key = keys == null ? rsmd.getColumnName(i) : keys[i - 1];
					if (rsmd.getColumnName(i).equals(blobCol)) {
						Blob blobOb = rs.getBlob(i);
						map.put(key,
								new String(blobOb.getBytes(1L, (int) blobOb.length())));
					} else
						map.put(key, rs.getString(i));
				}
				obs.add(map);
			}

			rs.close();
			ps.close();
			postprocess(c);

		} catch (SQLException e) {
			try {
				c.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return obs;
	}

	private Connection preprocess(Connection c, boolean atomic) throws SQLException {
		c = borrowObject(atomic);
		if (c == null)
			return null;
		c.setAutoCommit(false);
		return c;
	}

	private void postprocess(Connection c) throws SQLException {
		c.commit();
		returnObject(c, false);
	}

	public Integer executeProcedureGetInt(String procStr, boolean atomic, String[] values) throws SQLException {
		Connection c = null;
		ResultSet rs = null;
		try {
			if ((c = preprocess(c, atomic)) == null)
				return null;

			CallableStatement cs = c.prepareCall(procStr);
			cs.registerOutParameter(values.length + 1, Types.INTEGER);
			for (int i = 1; i <= values.length; i++) {
				cs.setString(i, values[i - 1]);
			}

			System.out.println("Executing: " + procStr);
			System.out.println("Args: " + values);

			cs.executeUpdate();
			postprocess(c);
			return cs.getInt(values.length + 1);

		} catch (SQLException e) {
			c.rollback();
			returnObject(c, false);
			e.printStackTrace();
			return null;
		}
	}

	private class ConnectionPoolThread extends Thread {
		@Override
		public void run() {
			while (true) {
				createPool(Constants.POOL_SIZE);
			}
		}
	}

	public Collection<Map<String, String>> fetch(String string, boolean atomic) {
		return fetch(string, null, atomic);
	}
	
	public Collection<Map<String, Object>> fetchBlob(String string, boolean atomic, String blobCol) {
		return fetchBlob(string, null, atomic, blobCol);
	}
}
