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
		List<BusinessObject> records = null;
		List<CodeList> codes = GetCodeList.getCodeList(new CodeList(null, "USER-ROLE", "EMPLOYEE", null));
		boolean result = false;
		CodeList code;
		UserInfo info;
		User user;
		
		if (employee != null && username != null && password != null & email != null && codes.size() == 1) {
			code = codes.get(0);
			DAOBusinessObject.insert(new User(null, code.getId(), username, password));
		}
		
		
		
		return false;
	}
}
