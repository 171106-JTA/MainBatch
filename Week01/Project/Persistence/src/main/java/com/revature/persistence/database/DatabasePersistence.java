package com.revature.persistence.database;

import java.sql.Statement;

import org.apache.log4j.Logger;

import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.factory.BusinessObjectFactory;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.database.util.ClauseBuilder;

public abstract class DatabasePersistence implements Persistenceable {
	protected static BusinessObjectFactory businessObjectFactory = BusinessObjectFactory.getFactory();
	protected static FieldParamsFactory fieldParamsFactory = FieldParamsFactory.getFactory();
	protected static ClauseBuilder clauseBuilder = ClauseBuilder.getBuilder();
	
	// Logger
	protected static Logger logger = Logger.getLogger(DatabasePersistence.class);
	
	public void setup(Object data) {
		// do nothing 
	}
	
	///
	// PROTECTED METHODS 
	///
	
	protected String validateTableName(String name) {
		switch (name.toLowerCase()) {
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
			case BusinessClass.USER:
				return BankTable.USER;
			case BusinessClass.USERINFO:
				return BankTable.USERINFO;
			case BusinessClass.CREDIT:
				return BankTable.CREDIT_ACCOUNT_VIEW;
			case BusinessClass.CHECKING:
				return BankTable.CHECKING_ACCOUNT_VIEW;
			case BusinessClass.ACCOUNT:
				return BankTable.ACCOUNT;
			default:
				return null;
		}
	}
}
