package com.revature.core.factory;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
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
			case "checking":
			case "credit":
				return convertAccountToFieldParams((Account)businessObject);
			default:
				return null;
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Converts user instance into FieldParams instance 
	 * @param user what to convert
	 * @return FieldParams representation of user
	 */
	private FieldParams convertUserToFieldParams(User user) {
		FieldParams params = new FieldParams();
		
		// Set field param data 
		params.put("id", Long.toString(user.getId()));
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("role", Integer.toString(user.getRole().ordinal()));
		
		return params;
	}
	
	/**
	 * Converts userInfo instance into FieldParams instance 
	 * @param userInfo what to convert
	 * @return FieldParams representation of userInfo
	 */
	private FieldParams convertUserInfoToFieldParams(UserInfo userInfo) {
		FieldParams params = new FieldParams();
		
		// Set field param data 
		params.put("userid", Long.toString(userInfo.getUserId()));
		params.put("email", userInfo.getEmail());
		params.put("address", userInfo.getAddress());
		params.put("phonenumber", userInfo.getPhonenumber());
		
		return null;
	}
	
	/**
	 * Converts account instance into FieldParams instance 
	 * @param acct what to convert
	 * @return FieldParams representation of account
	 */
	private FieldParams convertAccountToFieldParams(Account acct) {
		// Convert based on type
		switch (acct.getType()) {
			case CHECKING:
				return convertCheckingToFieldParams((Checking)acct);
			case CREDIT:
				return convertCreditToFieldParams((Credit)acct);
			default:
				return null;
		}
	}
	
	/**
	 * Converts checking account instance into FieldParams instance 
	 * @param checking what to convert
	 * @return FieldParams representation of account
	 */
	private FieldParams convertCheckingToFieldParams(Checking checking) {
		FieldParams params = new FieldParams();
		
		// Set field param data 
		params.put("userid", Long.toString(checking.getUserId()));
		params.put("number", Long.toString(checking.getNumber()));
		params.put("total", Float.toString(checking.getTotal()));
		params.put("type", Integer.toString(checking.getType().ordinal()));
		
		return params;
	}
	
	/**
	 * Converts credit account instance into FieldParams instance 
	 * @param acct what to convert
	 * @return FieldParams representation of account
	 */
	private FieldParams convertCreditToFieldParams(Credit credit) {
		FieldParams params = new FieldParams();
		
		// Set field param data 
		params.put("userid", Long.toString(credit.getUserId()));
		params.put("number", Long.toString(credit.getNumber()));
		params.put("total", Float.toString(credit.getTotal()));
		params.put("interest", Float.toString(credit.getInterest()));
		params.put("creditlimit", Float.toString(credit.getCreditLimit()));
		params.put("type", Integer.toString(credit.getType().ordinal()));
		
		return params;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
