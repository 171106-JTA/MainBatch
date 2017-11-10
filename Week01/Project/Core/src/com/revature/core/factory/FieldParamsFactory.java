package com.revature.core.factory;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;

/**
 * Ued to generate FieldParams from BusinessObject Instances 
 * @author Antony Lulciuc
 */
public class FieldParamsFactory {
	private static FieldParamsFactory factory;
	
	private FieldParamsFactory() {
		// do nothing
	}
	
	public static FieldParamsFactory getFactory() {
		return factory == null ? factory = new FieldParamsFactory() : factory;
	}
	
	public FieldParams getFieldParams(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case "user":
			case "admin":
			case "customer":
				return convertUserToFieldParams((User)businessObject);
			case "userinfo":
				return convertUserInfoToFieldParams((UserInfo)businessObject);
			case "account":
				return convertAccountToFieldParams((Account)businessObject);
			default:
				return null;
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private FieldParams convertUserToFieldParams(User user) {
		return null;
	}
	
	private FieldParams convertUserInfoToFieldParams(UserInfo userInfo) {
		return null;
	}
	
	private FieldParams convertAccountToFieldParams(Account acct) {
		return null;
	}
	
}
