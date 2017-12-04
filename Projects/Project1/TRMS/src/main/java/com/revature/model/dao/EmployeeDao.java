package com.revature.model.dao;

import java.util.Collection;
import com.revature.model.bean.Employee;
import com.revature.util.JDBC;

public class EmployeeDao implements Dao<Employee> {
	private final String empCreateSP = "CALL: CREATE_EMPLOYEE(?, ?, ?, ?, ?)";
	private final String empUpdateSP = "CALL: CREATE_EMPLOYEE(?, ?, ?, ?, ?)";
	private final String empDeleteSP = "CALL: CREATE_EMPLOYEE(?, ?, ?, ?, ?)";
	private final String empFetch = "SELECT EMP_ID, EMP_TYPE, USERNAME, PASSWORDS FROM EMPLOYEE "
			+ "NATURAL JOIN EMPLOYEE_TYPE NATURAL JOIN USERNAMES NATURAL JOIN PASSWORDS";
	private String[] values = new String[4];

	@Override
	public boolean insert(boolean atomic) {

		return false;
	}

	@Override
	public boolean update(boolean atomic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(boolean atomic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee fetch(boolean atomic) {
		JDBC.getInstance().fetch();
		return null;
	}

	@Override
	public Collection<Employee> fetchAll(boolean atomic) {
		// TODO Auto-generated method stub
		return null;
	}

}
