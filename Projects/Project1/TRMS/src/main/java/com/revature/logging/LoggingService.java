package com.revature.logging;

import org.apache.log4j.Logger;

public class LoggingService {

	private static Logger log;
	
	public static synchronized Logger getLogger() {
		if(log == null) {
			log = Logger.getRootLogger();
		}
		return log;
	}
}