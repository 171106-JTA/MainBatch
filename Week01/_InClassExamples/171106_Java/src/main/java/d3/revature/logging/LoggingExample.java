package d3.revature.logging;

import org.apache.log4j.Logger;

public class LoggingExample {

	final static Logger logger = Logger.getLogger(LoggingExample.class);
	
	public static void main(String[] args) {
		LoggingExample le = new LoggingExample();
		le.logStuff("My first log!");

	}
	
	public void logStuff(String message){
		
		logger.trace("Approaching exception.");
		try{
			int i = 1/0;
			logger.info("No exception occurred");
		}catch(Exception e){
			logger.info("Exception occurred: " + e.getMessage());
		}
		
		logger.trace(message);
		logger.debug(message);
		logger.info(message);
		logger.warn(message);
		logger.error(message);
		logger.fatal(message);
	}

}
