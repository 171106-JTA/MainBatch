package com.revature.log;

import org.apache.log4j.Logger;

public class Logging {
	//Only one logger is need, so a singleton was implemented
	
	final static Logger logger = Logger.getLogger(Logging.class);
	static int instanceCount = 0;
	private static Logging log;
	
	private Logging() {
		instanceCount++;
	}
	
	private static Logging getLogging() {
		if(log == null) {
			log = new Logging();
		}
		return log;
	}
	
	public static void startLogging(String m) {
		if(log == null) {
			log = Logging.getLogging();
		}
		logger.info(m);
	}
}
