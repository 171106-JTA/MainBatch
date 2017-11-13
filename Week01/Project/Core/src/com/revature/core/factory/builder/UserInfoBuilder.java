package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.core.FieldParams;

/**
 * Initializes Objects of type UserInfo
 * @author Antony Lulciuc
 */
public class UserInfoBuilder implements BusinessObjectBuilder {

	/**
	 * Creates UserInfo instance 
	 * @param args used to instantiate instance 
	 * @return new UserInfo on success else null
	 */
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		// TODO Auto-generated method stub
		return isValid(args) ? new UserInfo(Long.parseLong(args.get(UserInfo.USERID)), 
				args.get(UserInfo.EMAIL), args.get(UserInfo.ADDRESS), args.get(UserInfo.PHONENUMBER)) : null;
	}

	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	@Override
	public boolean isValid(FieldParams args) {
		// TODO Auto-generated method stub
		return args != null && args.get(UserInfo.USERID) != null;
	}

}
