package com.revature.core.factory;

import com.revature.businessobject.BusinessObject;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.factory.builder.AccountBuilder;
import com.revature.core.factory.builder.BusinessObjectBuilder;
import com.revature.core.factory.builder.UserBuilder;
import com.revature.core.factory.builder.UserInfoBuilder;

/**
 * 
 * @author Antony Lulciuc
 */
public class BusinessObjectFactory {
	private static BusinessObjectFactory factory;
	
	// Factory builders 
	private static BusinessObjectBuilder userBuilder;
	private static BusinessObjectBuilder userInfoBuilder;
	private static BusinessObjectBuilder accountBuilder;
	
	/**
	 * Initializes builders
	 */
	private BusinessObjectFactory() {
		userBuilder = new UserBuilder();
		userInfoBuilder = new UserInfoBuilder();
		accountBuilder = new AccountBuilder();
	}
	
	/**
	 * @return Instance of self
	 */
	public static BusinessObjectFactory getFactory() {
		return factory == null ? factory = new BusinessObjectFactory() : factory;
	}
	
	/**
	 * Creates new BusinessObject Instance
	 * @param name desired BusinessObject class name
	 * @param args values used to initialize object
	 * @return new BusinessObject on success else null
	 */
	public BusinessObject getBusinessObject(String name, FieldParams args) {
		switch (name.toLowerCase()) {
			case BusinessClass.USER:
				return userBuilder.getBusinessObject(args);
			case BusinessClass.USERINFO:
				return userInfoBuilder.getBusinessObject(args);
			case BusinessClass.ACCOUNT:
				return accountBuilder.getBusinessObject(args);
			default:
				return null;
		}
	}
	
}
