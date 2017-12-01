package com.revature.model.pattern.factory;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class BankingLoggerFactory implements LoggerFactory {

	@Override
	public Logger makeNewLoggerInstance(String arg0) {
		return Logger.getLogger(arg0);
	}

}
