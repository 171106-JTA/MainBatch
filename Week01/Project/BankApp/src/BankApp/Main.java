package BankApp;

import org.apache.log4j.Logger;

/**
 * @author Matthew
 * Main Class for running the BankApp
 */
public class Main {
	
	private static final Logger log = Logger.getLogger("GLOBAL");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankDriver driver = new BankDriver();
		log.info("Main: Starting BankDriver");
		driver.start();
	}

}
