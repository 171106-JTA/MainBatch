package com.revature.service;

import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.CompanyEmployee;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.dao.DAOBusinessObject;

public class RegisterEmployee {
	
	/**
	 * Attempts to locate employee 
	 * @param employeeId - id provided by company 
	 * @param firstname 
	 * @param lastname
	 * @return handle to CompanyEmployee instance if found else null.
	 */
	public static CompanyEmployee validateEmployee(Integer employeeId, String firstname, String lastname) {
		List<BusinessObject> records = null;
		CompanyEmployee employee = null;
		CompanyEmployee find;
		
		// If valid arguments supplied 
		if (employeeId != null && firstname != null && lastname != null) {
			// Prepare query data
			find = new CompanyEmployee();
			find.setEmployeeId(employeeId);
			find.setFirstName(firstname);
			find.setLastName(lastname);
			
			// Perform query
			records = DAOBusinessObject.load(find);
			
			// did we find the employee ?
			if (records.size() == 1)
				employee = (CompanyEmployee)records.get(0);
		}
		
		return employee;
	}
	
	/**
	 * Registers account for employee
	 * @param employee - data about employee provided by company
	 * @param username
	 * @param password 
	 * @param email
	 * @return true if account made else false
	 */
	public static boolean registerAccount(CompanyEmployee employee, String username, String password, String email) {
		List<CodeList> codes = GetCodeList.getCodeList(new CodeList(null, "USER-ROLE", "EMPLOYEE", null));
		List<BusinessObject> records = null;
		boolean result = false;
		CodeList code;
		User user;
		
		// If valid user data supplied 
		if (validateAcctRegArgs(employee, username, password, email) && codes.size() == 1) {
			code = codes.get(0);
			
			// Create User 
			DAOBusinessObject.insert(user = new User(null, code.getId(), username, password));
			
			// Get newly created account
			if ((records = DAOBusinessObject.load(user)).size() == 1) {
				user = (User)records.get(0);
				
				// Create user Information, if failed then delete user record
				if (!(result = DAOBusinessObject.insert(new UserInfo(user.getId(), employee)) == 1))
					DAOBusinessObject.delete(user);
			}
		}
		
		return result;
	}
	
	
	
	///
	//	PRIVATE METHODS 
	///
	
	private static boolean validateAcctRegArgs(CompanyEmployee employee, String username, String password, String email) {
		boolean result = employee != null;
		
		result = result && username != null;
		result = result && password != null;
		result = result && email != null;
		
		return result;
	}
	
}
