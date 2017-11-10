package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
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
			switch (AccountType.values()[Integer.parseInt(args.get("type"))]) {
				case CHECKING:
					object =  new Checking(Long.parseLong(args.get("userid")), 
										Long.parseLong(args.get("number")),
										Float.parseFloat("total"));
					break;
				case CREDIT:
					object =  new Credit(Long.parseLong(args.get("userid")),
									  Long.parseLong(args.get("number")),
									  Float.parseFloat(args.get("total")),
									  Float.parseFloat(args.get("interest")),
									  Float.parseFloat(args.get("creditlimit")));
					break;
			}
		}

		return object;
	}

	@Override
	public boolean isValid(FieldParams args) {
		return args != null && args.get("userid") != null && args.get("number") != null && args.get("type") != null;
	}

}
