package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.core.FieldParams;

public class UserInfoBuilder implements BusinessObjectBuilder {

	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		// TODO Auto-generated method stub
		return isValid(args) ? new UserInfo(Long.parseLong(args.get(UserInfo.USERID)), 
				args.get(UserInfo.EMAIL), args.get(UserInfo.ADDRESS), args.get(UserInfo.PHONENUMBER)) : null;
	}

	@Override
	public boolean isValid(FieldParams args) {
		// TODO Auto-generated method stub
		return args != null && args.get(UserInfo.USERID) != null;
	}

}
