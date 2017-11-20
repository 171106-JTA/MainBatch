package com.revature.core.factory;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Type;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;

/**
 * Used to generate FieldParams from BusinessObject Instances 
 * @author Antony Lulciuc
 */
public class FieldParamsFactory {
	private static FieldParamsFactory factory;
	
	/**
	 * Is singleton
	 */
	private FieldParamsFactory() {
		// do nothing
	}
	
	/**
	 * @return self
	 */
	public static FieldParamsFactory getFactory() {
		return factory == null ? factory = new FieldParamsFactory() : factory;
	}
	
	/**
	 * Creates FieldParams Object Instances from BusinessObjects
	 * @param businessObject- what to convert to FieldParams Instance 
	 * @return FielParams representation of BusinessObject if conversion defined else null
	 */
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
		params.put(UserInfo.SSN, Long.toString(userInfo.getSsn()));
		params.put(UserInfo.USERID, Long.toString(userInfo.getUserId()));
		params.put(UserInfo.EMAIL, userInfo.getEmail());
		params.put(UserInfo.PHONENUMBER, userInfo.getPhonenumber());
		params.put(UserInfo.ADDRESS1, userInfo.getAddress1());
		params.put(UserInfo.ADDRESS2, userInfo.getAddress2());
		params.put(UserInfo.FIRSTNAME, userInfo.getFirstname());
		params.put(UserInfo.LASTNAME, userInfo.getLastname());
		params.put(UserInfo.POSTALCODE, userInfo.getPostalcode());
		params.put(UserInfo.STATECITYID, Long.toString(userInfo.getStateCityId()));
		params.put(UserInfo.STATUSID, Long.toString(userInfo.getStatusId()));
		
		return params;
	}
	
	/**
	 * Converts account instance into FieldParams instance 
	 * @param acct what to convert
	 * @return FieldParams representation of account
	 */
	private FieldParams convertAccountToFieldParams(Account acct) {
		// Convert based on type
		switch (acct.getType().toLowerCase()) {
			case Type.CHECKING:
				return convertCheckingToFieldParams(acct);
			case Type.CREDIT:
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
	private FieldParams convertCheckingToFieldParams(Account checking) {
		FieldParams params = new FieldParams();
		
		// Set field param data 
		params.put(Account.USERID, Long.toString(checking.getUserId()));
		params.put(Account.TYPEID, Long.toString(checking.getTypeId()));
		params.put(Account.STATUSID, Long.toString(checking.getStatusId()));
		params.put(Account.BALANCE, Float.toString(checking.getBalance()));
		params.put(Account.NUMBER, checking.getNumber());
		
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
		params.put(Credit.TYPEID, Long.toString(credit.getTypeId()));
		params.put(Credit.STATUSID, Long.toString(credit.getStatusId()));
		params.put(Credit.RATEID, Long.toString(credit.getRateId()));
		params.put(Credit.NUMBER, credit.getNumber());
		params.put(Credit.BALANCE, Float.toString(credit.getBalance()));
		params.put(Credit.MINIMALPAYMENTDUE, Float.toString(credit.getMinimalPaymentDue()));
		params.put(Credit.CREDITLIMIT, Float.toString(credit.getCreditLimit()));
		
		return params;
	}
}
