package com.revature.core.factory.builder;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.account.Type;
import com.revature.core.FieldParams;

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
											Long.parseLong(args.get(Checking.NUMBER)),
											Float.parseFloat(args.get(Checking.BALANCE)),
											args.get(Checking.STATUSID));
						break;
					case Type.CREDIT:
						object =  new Credit(Long.parseLong(args.get(Credit.USERID)),
										  Long.parseLong(args.get(Credit.NUMBER)),
										  Float.parseFloat(args.get(Credit.TOTAL)),
										  Float.parseFloat(args.get(Credit.INTEREST)),
										  Float.parseFloat(args.get(Credit.CREDITLIMIT)),
										  args.get(Checking.STATUSID));
						break;
				}
			} catch (NumberFormatException e) {
				logger.error(e);
			}
		}

		return object;
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
				result = result && args.get(Credit.TOTAL) != null;
				result = result && args.get(Credit.INTEREST) != null;
				result = result && args.get(Credit.CREDITLIMIT) != null;
				break;
		}
		
		return result;
	}
}
