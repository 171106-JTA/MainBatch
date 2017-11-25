package com.revature.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * Creates and manages a set of database connections throughout the life of the application
 * @author Antony Lulciuc
 */
public final class ConnectionPool {
	private static final int INVALID_DATABASE_CONNECTION_HANDLE = Integer.MIN_VALUE;
	private static final int DEFAULT_CONNECTION_COUNT = 5;
	private static Queue<Connection> connectionsFree = new PriorityQueue<>();
	private static Map<Integer, Connection> connectionsInUse = new HashMap<>();
	private Logger logger = Logger.getLogger(ConnectionPool.class); 
	private static ConnectionPool pool;
	
	/**
	 * Initializes connection pool
	 * @param numberOfConnections
	 */
	private ConnectionPool(int numberOfConnections) {
		Connection conn;
		
		for (int i = 0; i < numberOfConnections; i++) {
			if ((conn = createConnection()) != null)
				connectionsFree.add(conn);
			else {
				System.err.println("Failed to establish connection to database...");
				close();
				System.exit(INVALID_DATABASE_CONNECTION_HANDLE);
			}
		}
	}

	/**
	 * Creates connection pool with DEFAULT_CONNECTION_COUNT connections IFF not initialized 
	 * @return Handle to connection pool 
	 */
	public static ConnectionPool getPool() {
		return pool == null ? pool = new ConnectionPool(DEFAULT_CONNECTION_COUNT) : pool;
	}
	
	/**
	 * Creates connection pool with n connections IFF not initialized 
	 * @param numberOfConnections - number of connections to create 
	 * @return Handle to connection pool
	 */
	public static ConnectionPool getPool(int numberOfConnections) {
		return pool == null ? pool = new ConnectionPool(numberOfConnections) : pool;
	}
	
	/**
	 * @return next free connection, null if no connection available 
	 */
	public synchronized static Connection getConnection() {
		Connection conn = null;
		
		if (connectionsFree.size() > 0) {
			conn = connectionsFree.poll();
			connectionsInUse.put(conn.hashCode(), conn);
		}
		
		return conn;
	}
	
	/**
	 * Returns connection back to pool
	 * @param conn - connection to return 
	 */
	public synchronized void releaseConnection(Connection conn) {
		connectionsInUse.remove(conn.hashCode());
		connectionsFree.add(conn);
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
     * Initializes new connection
	 * @return connection to database
	 */
	private Connection createConnection() {
		String[] prop = System.getenv("DBProp").split(";");
		Connection conn = null;
		int connectionNum = connectionsFree.size() + 1;
		
		logger.debug("attempting to open connection [" + connectionNum + "]");
		
		try {
			conn = DriverManager.getConnection(prop[1], prop[2], prop[3]);
		}catch (SQLException e) {
			logger.error("failed to open connection [" + connectionNum + "], message=" + e);
		}
		
		return conn;
	}
	
	/**
	 * Closes all connections 
	 */
	private synchronized void close() {
		logger.debug("closing connections...");
		
		for (Integer key : connectionsInUse.keySet()) {
			try {
				connectionsInUse.get(key).close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.debug("failed to close connection, message=" + e);
			}
		}
		
		for (Connection conn : connectionsFree) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.debug("failed to close connection, message=" + e);
			}
		}
		
		connectionsInUse.clear();
		connectionsFree.clear();
	}
	
	/**
	 * Closes all connections 
	 */
	@Override
	protected void finalize() {
		close();
	}
}
