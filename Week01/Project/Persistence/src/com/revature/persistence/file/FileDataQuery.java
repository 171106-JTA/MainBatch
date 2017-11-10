package com.revature.persistence.file;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.comparator.FieldParamsAccountComparator;
import com.revature.core.comparator.FieldParamsUserComparator;
import com.revature.core.comparator.FieldParamsUserInfoComparator;

public abstract class FileDataQuery extends FilePersistence {

	@Override
	public Resultset select(String name, FieldParams cnds) {
		// Log request
		logger.debug("select:>" + name + " with " + cnds);
				
		switch (name.toLowerCase()) {
			case "user":
				return findUser(cnds);
			case "account":
				return findAccount(cnds);
			case "userinfo":
				return findUserInfo(cnds);
			default:
				// invalid type
				logger.warn(name + " is unknown type");
				return null;
		}
	}

	///
	//	PROTECTED METHODS 
	///
	
	/**
	 * Finds indices of all users who meet specified conditions
	 * @param cnds what user must have 
	 * @return positions of users who met conditions specified  
	 */
	protected static List<Integer> findAllUserIndex(FieldParams cnds) {
		List<Integer> indices = new ArrayList<>();
		
		// If we have conditions 
		if (cnds != null) {
			FieldParamsUserComparator comparator = new FieldParamsUserComparator();;
			Iterator<User> it = users.iterator();
			
			// Loop through users to see who meets requirements 
			for (int i = 0; it.hasNext(); i++) {
				if (comparator.compare(cnds, it.next()) == 0)
					indices.add(i);
			}
		}
		
		return indices;
	}
	
	/**
	 * Finds indices of all userinfo who meet specified conditions
	 * @param cnds what userinfo must have 
	 * @return positions of userinfo who met conditions specified  
	 */
	protected static List<Integer> findAllUserInfoIndex(FieldParams cnds) {
		List<Integer> indices = new ArrayList<>();
		
		// If we have conditions 
		if (cnds != null) {
			FieldParamsUserInfoComparator comparator = new FieldParamsUserInfoComparator();;
			Iterator<UserInfo> it = userInfo.iterator();
			
			// Loop through users to see who meets requirements 
			for (int i = 0; it.hasNext(); i++) {
				if (comparator.compare(cnds, it.next()) == 0)
					indices.add(i);
			}
		}
		
		return indices;
	}
	
	/**
	 * Finds indices of all users who meet specified conditions
	 * @param cnds what user must have 
	 * @return positions of users who met conditions specified  
	 */
	protected static List<Integer> findAllAccountIndex(FieldParams cnds) {
		List<Integer> indices = new ArrayList<>();
		
		// If we have conditions 
		if (cnds != null) {
			FieldParamsAccountComparator comparator = new FieldParamsAccountComparator();
			Iterator<Account> it = accounts.iterator();
			
			// Loop through users to see who meets requirements 
			for (int i = 0; it.hasNext(); i++) {
				if (comparator.compare(cnds, it.next()) == 0)
					indices.add(i);
			}
		}
		
		return indices;
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	
	/**
	 * Attempts to locate user data with FieldParams
	 * @param cnds must meet all conditions to be added to result set
	 * @return all instances which meet conditions 
	 */
	private Resultset findUser(FieldParams cnds) {
		Resultset found = new Resultset();
		
		// if No conditions provided return all
		if (cnds == null)
			found.addAll(users);
		else {
			FieldParamsUserComparator comparator = new FieldParamsUserComparator();
			
			for (User user : users) {
				if (comparator.compare(cnds, user) == 0)
					found.add(user);
			}
		}
		
		return found;
	}
	
	/**
	 * Attempts to locate userinfo data with FieldPaams
	 * @param cnds must meet all conditions to be added to result set
	 * @return all instances which meet conditions 
	 */
	private Resultset findUserInfo(FieldParams cnds) {
		Resultset found = new Resultset();
		
		// if No conditions provided return all
		if (cnds == null)
			found.addAll(userInfo);
		else {
			FieldParamsUserInfoComparator comparator = new FieldParamsUserInfoComparator();
			
			for (UserInfo info : userInfo) {
				if (comparator.compare(cnds, info) == 0)
					found.add(info);
			}
		}
		
		return found;
	}
	
	/**
	 * Attempts to locate account data with FieldPaams
	 * @param cnds must meet all conditions to be added to result set
	 * @return all instances which meet conditions 
	 */
	private Resultset findAccount(FieldParams cnds) {
		Resultset found = new Resultset();
		
		// if No conditions provided return all
		if (cnds == null)
			found.addAll(accounts);
		else {
			FieldParamsAccountComparator comparator = new FieldParamsAccountComparator();
			
			for (Account account : accounts) {
				if (comparator.compare(cnds, account) == 0)
					found.add(account);
			}
		}
		
		return found;
	}
}
