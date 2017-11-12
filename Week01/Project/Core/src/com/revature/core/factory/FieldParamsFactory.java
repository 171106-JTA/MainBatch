package com.revature.core.factory;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountType;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
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
			case BusinessClass.USER:
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
				return convertUserToFieldParams((User)businessObject);
			case BusinessClass.USERINFO:
				return convertUserInfoToFieldParams((UserInfo)businessObject);
			case BusinessClass.ACCOUNT:
			case BusinessClass.CHECKING:
			case BusinessClass.CREDIT:
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
		params.put(User.ID, Long.toString(user.getId()));
		params.put(User.USERNAME, user.getUsername());
		params.put(User.PASSWORD, user.getPassword());
		params.put(User.CHECKPOINT, user.getCheckpoint());
		
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
		params.put(UserInfo.USERID, Long.toString(userInfo.getUserId()));
		params.put(UserInfo.EMAIL, userInfo.getEmail());
		params.put(UserInfo.ADDRESS, userInfo.getAddress());
		params.put(UserInfo.PHONENUMBER, userInfo.getPhonenumber());
		
		return params;
	}
	
	/**
	 * Converts account instance into FieldParams instance 
	 * @param acct what to convert
	 * @return FieldParams representation of account
	 */
	private FieldParams convertAccountToFieldParams(Account acct) {
		// Convert based on type
		switch (acct.getType()) {
			case AccountType.CHECKING:
				return convertCheckingToFieldParams((Checking)acct);
			case AccountType.CREDIT:
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
		params.put(Checking.USERID, Long.toString(checking.getUserId()));
		params.put(Checking.NUMBER, Long.toString(checking.getNumber()));
		params.put(Checking.TOTAL, Float.toString(checking.getTotal()));
		params.put(Checking.TYPE, checking.getType());
		params.put(Checking.STATUS, checking.getStatus());
		
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
		params.put(Credit.USERID, Long.toString(credit.getUserId()));
		params.put(Credit.NUMBER, Long.toString(credit.getNumber()));
		params.put(Credit.TOTAL, Float.toString(credit.getTotal()));
		params.put(Credit.INTEREST, Float.toString(credit.getInterest()));
		params.put(Credit.CREDITLIMIT, Float.toString(credit.getCreditLimit()));
		params.put(Credit.TYPE, credit.getType());
		params.put(Checking.STATUS, credit.getStatus());
		
		return params;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
