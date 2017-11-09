package d3.revature.loggin;

import org.apache.log4j.Logger;

public class LoggingExample {
	
	final static Logger logger = Logger.getLogger(LoggingExample.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoggingExample le = new LoggingExample();
		le.logStuff("WutFace");
	}
	
	public void logStuff(String msg) {
		logger.trace("Approaching Exception");
		try {
			int i = 1/0;
			logger.info("No exception occured");
		}catch(Exception e) {
			logger.info("Exception occured" + e.getMessage());
		}
		
		logger.trace(msg);
		logger.debug(msg);
		logger.info(msg);
		logger.warn(msg);
		logger.error(msg);
		logger.fatal(msg);
	}
	
}
