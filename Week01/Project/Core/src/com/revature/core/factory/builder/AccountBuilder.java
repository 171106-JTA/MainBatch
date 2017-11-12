package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountStatus;
import com.revature.businessobject.info.account.AccountType;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.core.FieldParams;

public class AccountBuilder implements BusinessObjectBuilder {

	@SuppressWarnings("incomplete-switch")
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		if (isValid(args)) {
			AccountStatus status = AccountStatus.values()[Integer.parseInt(args.get(Checking.STATUS))];
			
			switch (AccountType.values()[Integer.parseInt(args.get(Account.TYPE))]) {
				case CHECKING:
					object =  new Checking(Long.parseLong(args.get(Checking.USERID)), 
										Long.parseLong(args.get(Checking.NUMBER)),
										Float.parseFloat(args.get(Checking.TOTAL)),
										status);
					break;
				case CREDIT:
					object =  new Credit(Long.parseLong(args.get(Credit.USERID)),
									  Long.parseLong(args.get(Credit.NUMBER)),
									  Float.parseFloat(args.get(Credit.TOTAL)),
									  Float.parseFloat(args.get(Credit.INTEREST)),
									  Float.parseFloat(args.get(Credit.CREDITLIMIT)),
									  status);
					break;
			}
		}

		return object;
	}

	@Override
	public boolean isValid(FieldParams args) {
		return args != null && args.get(Account.USERID) != null && args.get(Account.NUMBER) != null && args.get(Account.TYPE) != null;
	}
}
