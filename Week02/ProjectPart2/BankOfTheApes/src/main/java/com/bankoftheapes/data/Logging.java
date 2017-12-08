package com.bankoftheapes.data;

import org.apache.log4j.Logger;

public class Logging {
	//Only one logger is needed, so a singleton was implemented
	
		final static Logger logger = Logger.getLogger(Logging.class);
		static int instanceCount = 0;
		private static Logging log;
		
		private Logging() {
			instanceCount++;
		}
		
		public static Logging getLogging() {
			if(log == null) {
				log = new Logging();
			}
			return log;
		}
		
		public void start(String message) {
			logger.info(message);
		}
}