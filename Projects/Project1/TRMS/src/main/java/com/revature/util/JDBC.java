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
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Semaphore;

public class JDBC implements Pool<Connection> {

	// Connection pool open/locked collections
	Set<Connection> locked = Collections.synchronizedSet(new HashSet<Connection>());
	Set<Connection> open = Collections.synchronizedSet(new HashSet<Connection>());
	public static final String URL = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:xe";
	public static final String USER = "Sean";
	public static final String PASS = "sean1234";

	// Connection pool semaphore
	private Semaphore sem;

	// JDBC singleton instance
	private static JDBC jdbc;

	private JDBC() {

		// initialize a pool of connections
		// pool.createPool(DBProperties.POOL_SIZE);
		createPool(50);
	}

	public static JDBC getInstance() {
		if (jdbc == null)
			jdbc = new JDBC();
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
				if (open.add(DriverManager.getConnection(URL)))
					;
				i++;
			} catch (SQLException e) {
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

	public boolean executeProcedure(String procStr, boolean atomic, String[] values, boolean blob, int blobIdx,
			String path) throws IOException {
		FileInputStream fis = null;
		Connection c = null;
		File file = null;
		try {
			if ((c = preprocess(c, atomic)) == null)
				return false;

			CallableStatement cs = c.prepareCall(procStr);
			for (int i = 0; i < values.length; i++) {
				if (blob && i == blobIdx) {
					file = new File(path);
					fis = new FileInputStream(file);
					cs.setBinaryStream(blobIdx, fis, file.length());
				}
				else {
					cs.setString(i, values[i]);
				}

			}
			cs.executeQuery();

			if (fis != null)
				fis.close();
			postprocess(c);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Collection<Map<String, String>> fetch(String queryStr, boolean atomic, boolean blob, int blobIdx) {
		Collection<Map<String, String>> obs = new LinkedHashSet<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		int size;
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
			for (int i = 0; i < size; i++) {
				map.put(rsmd.getColumnName(i), rsmd.getColumnName(i));
			}
			while (rs.next()) {
				for (int i = 0; i < size; i++) {
					if (blob && blobIdx == i) {
						Blob blobOb = rs.getBlob(i);
						map.put(rsmd.getColumnName(i), new String(blobOb.getBytes(1L, (int) blobOb.length())));
					} else
						map.put(rsmd.getColumnName(i), rs.getString(i));
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
}
