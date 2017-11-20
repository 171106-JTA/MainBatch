package com.revature.implementations;

import org.apache.log4j.Logger;

public class MyLogger {

	final static Logger logger = Logger.getLogger(MyLogger.class);
	
	public static void main(String[] args) {
		MyLogger log = new MyLogger();
		log.logTransactions("Bank App Log!");
	}
	
	public void logTransactions(String message){
		logger.info(message);
		logger.trace(message);
	}

}
