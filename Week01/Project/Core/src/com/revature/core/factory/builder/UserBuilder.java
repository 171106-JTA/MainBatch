package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.UserRole;
import com.revature.core.FieldParams;

public class UserBuilder implements BusinessObjectBuilder {

	@SuppressWarnings("incomplete-switch")
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		if (!isValid(args))
			return null;

		// Create instance based on user role
		switch (UserRole.values()[Integer.parseInt(args.get("role"))]){
			case ADMIN:
				object = new Admin(Long.parseLong(args.get("id")), args.get("username"), args.get("password"));
				break;
			case CUSTOMER:
				object = new Customer(Long.parseLong(args.get("id")), args.get("username"), args.get("password"));
				break;
		}
		
		return object;
	}

	@Override
	public boolean isValid(FieldParams args) {
		return args != null && args.get("role") != null && args.get("id") != null;
	}
}
