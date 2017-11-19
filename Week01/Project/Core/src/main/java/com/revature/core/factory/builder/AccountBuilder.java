package com.revature.core.factory.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.account.Type;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;

/**
 * Initializes Objects of type Checking/Credit 
 * @author Antony Lulciuc
 */
public class AccountBuilder implements BusinessObjectBuilder {
	private static Logger logger = Logger.getLogger(AccountBuilder.class);
	
	/**
	 * Creates Account instance 
	 * @param args used to instantiate instance 
	 * @return new Account on success else null
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		// if data valid
		if (isValid(args)) {
			try {
				switch (args.get(Account.TYPEID)) {
					case Type.CHECKING:
						object =  new Checking(Long.parseLong(args.get(Checking.USERID)), 
											args.get(Checking.NUMBER),
											Long.parseLong(args.get(Checking.TYPEID)),
											Long.parseLong(args.get(Checking.STATUSID)),
											args.get(Checking.CREATED),
											Float.parseFloat(args.get(Checking.BALANCE)));
						break;
					case Type.CREDIT:
						object =  new Credit(Long.parseLong(args.get(Credit.USERID)), 
											args.get(Credit.NUMBER),
											Long.parseLong(args.get(Credit.TYPEID)),
											Long.parseLong(args.get(Credit.STATUSID)),
											args.get(Credit.CREATED),
											Float.parseFloat(args.get(Credit.BALANCE)),
											Float.parseFloat(args.get(Credit.MINIMALPAYMENTDUE)),
											Float.parseFloat(args.get(Credit.CREDITLIMIT)),
											Long.parseLong(args.get(Credit.RATEID)));
						break;
				}
			} catch (NumberFormatException e) {
				logger.error(e);
			}
		}

		return object;
	}

	
	public Resultset getBusinessObject(ResultSet res) {
		Resultset data = new Resultset();
		Account acct;
		
		try {
			while (res.next()) {
				acct = new Account(res.getLong(Account.USERID), res.getString(Account.NUMBER), res.getLong(Account.TYPEID),
						res.getLong(Account.STATUSID), res.getString(Account.CREATED), Type.ACCOUNT);
				
				data.add(acct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	@Override
	public boolean isValid(FieldParams args) {
		boolean result = args != null;
		
		result = result && args.get(Account.USERID) != null;
		result = result && args.get(Account.NUMBER) != null;
		result = result && args.get(Account.TYPEID) != null;
		result = result && args.get(Account.STATUSID) != null;
		
		switch (args.get(Account.TYPEID)) {
			case Type.CHECKING:
				result = result && args.get(Checking.BALANCE) != null;
				break;
			case Type.CREDIT:
				result = result && args.get(Credit.BALANCE) != null;
				result = result && args.get(Credit.MINIMALPAYMENTDUE) != null;
				result = result && args.get(Credit.CREDITLIMIT) != null;
				result = result && args.get(Credit.RATEID) != null;
				break;
		}
		
		return result;
	}
}
