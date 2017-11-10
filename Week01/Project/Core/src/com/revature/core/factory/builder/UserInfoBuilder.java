package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.UserInfo;
import com.revature.core.FieldParams;

public class UserInfoBuilder implements BusinessObjectBuilder {

	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		// TODO Auto-generated method stub
		return isValid(args) ? new UserInfo(Long.parseLong(args.get("userid")), args.get("email"), args.get("address"), args.get("phonenumber")) : null;
	}

	@Override
	public boolean isValid(FieldParams args) {
		// TODO Auto-generated method stub
		return args != null && args.get("userid") != null;
	}

}
