package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.businessobject.user.Checkpoint;
import com.revature.core.FieldParams;

public class UserBuilder implements BusinessObjectBuilder {

	@SuppressWarnings("incomplete-switch")
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		if (!isValid(args))
			return null;

		// Create instance based on user role
		switch (args.get(User.CHECKPOINT)) {
			case Checkpoint.ADMIN:
				object = new Admin(Long.parseLong(args.get(User.ID)), args.get(User.USERNAME), args.get(User.PASSWORD));
				break;
			case Checkpoint.CUSTOMER:
				object = new Customer(Long.parseLong(args.get(User.ID)), args.get(User.USERNAME), args.get(User.PASSWORD));
				break;
			case Checkpoint.NONE:
			case Checkpoint.PENDING:
				object = new User(Long.parseLong(args.get(User.ID)), args.get(User.USERNAME), args.get(User.PASSWORD), Checkpoint.PENDING);
				break;
			case Checkpoint.BLOCKED:
				object = new User(Long.parseLong(args.get(User.ID)), args.get(User.USERNAME), args.get(User.PASSWORD), Checkpoint.BLOCKED);
				break;
		}
		
		return object;
	}

	@Override
	public boolean isValid(FieldParams args) {
		return args != null && args.get(User.CHECKPOINT) != null && args.get(User.ID) != null;
	}
}
