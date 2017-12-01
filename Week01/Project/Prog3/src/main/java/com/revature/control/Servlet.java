package com.revature.control;

import java.net.*;

public class Servlet implements Runnable, Observer {
	private Socket socket;
	
	public Servlet(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static void main(String[] args) {		
		// Initialize JDBC
		
		// Initialize Connection Pool
		
		// Initialize Server
	}
	

}
