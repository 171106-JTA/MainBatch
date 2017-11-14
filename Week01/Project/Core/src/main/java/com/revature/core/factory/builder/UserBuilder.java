package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.businessobject.user.Checkpoint;
import com.revature.core.FieldParams;

/**
 * Initializes Objects of type User 
 * @author Antony Lulciuc
 */
public class UserBuilder implements BusinessObjectBuilder {

	/**
	 * Creates User instance 
	 * @param args used to instantiate instance 
	 * @return new User on success else null
	 */
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

	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	@Override
	public boolean isValid(FieldParams args) {
		return args != null && args.get(User.CHECKPOINT) != null && args.get(User.ID) != null;
	}
}
