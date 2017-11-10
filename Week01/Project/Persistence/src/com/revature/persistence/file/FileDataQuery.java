package com.revature.persistence.file;

import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.comparator.FieldParamsUserComparator;

public abstract class FileDataQuery extends FilePersistence {

	@Override
	public Resultset select(String name, FieldParams cnds) {
		// Log request
		logger.debug("select:>" + name + " with " + cnds.toString());
				
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
		return null;
	}
	
	/**
	 * Attempts to locate account data with FieldPaams
	 * @param cnds must meet all conditions to be added to result set
	 * @return all instances which meet conditions 
	 */
	private Resultset findAccount(FieldParams cnds) {
		return null;
	}
}
