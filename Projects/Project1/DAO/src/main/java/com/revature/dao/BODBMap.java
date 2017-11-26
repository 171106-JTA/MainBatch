package com.revature.dao;

import java.util.Map;
import java.util.TreeMap;

import com.revature.businessobject.*;

/**
 * Maps Java BusinessObject Classes to Database Tables 
 * @author Antony Lulciuc
 */
public final class BODBMap {
	private static Map<String, String> mapping = new TreeMap<>();
	private static BODBMap map = new BODBMap();
	
	/**
	 * Initializes mapping of Java to Database Tables 
	 */
	private BODBMap() {
		init();
	}
	
	/**
	 * @return singleton
	 */
	public static BODBMap getMap() {
		return map;
	}
	
	/**
	 * Get value associated with Java Class
	 * @param key - simple BusinessObject Class name
	 * @return database table name represented by java class name
	 */
	public String get(String key) {
		return mapping.get(key.toLowerCase());
	}
	
	/**
	 * Do we have this class mapped to a table?
	 * @param key - class we wish to check
	 * @return true if key is mapped to table else false
	 */
	public boolean containsKey(String key) {
		return mapping.containsKey(key);
	}
	
	///
	// PRIVATE METHODS
	///
	
	/**
	 * Maps Java BusinessObject Classes to Database Tables
	 */
	private void init() {
		mapping.put(User.class.getSimpleName().toLowerCase(), "EAR_USER");
		mapping.put(UserInfo.class.getSimpleName().toLowerCase(), "EAR_USER_INFO");
		mapping.put(CodeList.class.getSimpleName().toLowerCase(), "CODE_LIST");
		mapping.put(Form.class.getSimpleName().toLowerCase(), "EAR_FORM");
		mapping.put(BenefitCoordinator.class.getSimpleName().toLowerCase(), "EAR_BENEFIT_COORDINATOR");
		mapping.put(FormAssignee.class.getSimpleName().toLowerCase(), "EAR_FORM_ASSIGNEE");
		mapping.put(FormAttachment.class.getSimpleName().toLowerCase(), "EAR_FORM_ATTACHMENT");
		mapping.put(FormStatus.class.getSimpleName().toLowerCase(), "EAR_FORM_STATUS");
		mapping.put(Message.class.getSimpleName().toLowerCase(), "EAR_MESSAGE");
		mapping.put(MessageAttachment.class.getSimpleName().toLowerCase(), "EAR_MESSAGE_ATTACHMENT");
		mapping.put(Recipient.class.getSimpleName().toLowerCase(), "EAR_RECIPIENT");
		mapping.put(Supervisor.class.getSimpleName().toLowerCase(), "EAR_SUPERVISOR");
	}
}
