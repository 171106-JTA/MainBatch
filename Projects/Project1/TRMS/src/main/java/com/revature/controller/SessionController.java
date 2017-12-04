package com.revature.controller;

import com.revature.util.JDBC;

public class SessionController {

	static int login(String user, String pass, String type) {
		return 0;
	}

	static int register(String user, String pass, String superId, int type) {
		return 0;
	}

	public static void startJDBC() {
		// invoke a simple worker thread to start jdbc
		new JDBCStarterThread().start();
	}

	private static class JDBCStarterThread extends Thread {

		@Override
		public void run() {
			JDBC.getInstance();
		}
	}
}
