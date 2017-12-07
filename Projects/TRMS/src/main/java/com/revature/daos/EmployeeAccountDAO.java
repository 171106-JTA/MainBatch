package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.EmployeeAccount;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class EmployeeAccountDAO {
	public static boolean insertEmployeeAccount(EmployeeAccount employeeaccount) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		String[] sqlcolumns = new String[1];
		sqlcolumns[0] = "employee_id";
		String[] sqlvalues = new String[1];
		String sql;
		sqlvalues[0] = "" + employeeaccount.getEmployeeid();
		int rowsaffected;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		if(dataconnection.exists("Employees", sqlcolumns, sqlvalues)) {
			return false;
		}
		sql = "INSERT INTO EmployeeAccounts(username, password, first_name, middle_name, last_name, email, address, city, state, is_locked, is_admin, employee_id)"
				+ " VALUES("
					+ "'" + employeeaccount.getUsername() + "', "
					+ "'" + employeeaccount.getPassword() + "', "
					+ "'" + employeeaccount.getFirstname() + "', "
					+ "'" + employeeaccount.getMiddlename() + "', "
					+ "'" + employeeaccount.getLastname() + "', "
					+ "'" + employeeaccount.getEmail() + "', "
					+ "'" + employeeaccount.getAddress() + "', "
					+ "'" + employeeaccount.getCity() + "', "
					+ "'" + employeeaccount.getState() + "', ";
					if(employeeaccount.isLocked() == true) {
						sql +=  "is_locked = 1, ";
					} else {
						sql += "is_locked = 0, ";
					}
					if(employeeaccount.isAdmin() == true) {
						sql +=  "is_admin = 1, ";
					} else {
						sql += "is_admin = 0, ";
					}
					sql += employeeaccount.getEmployeeid()
					+ ");";
		rowsaffected = dataconnection.requestQuery(sql, "COMMIT;");
		if(rowsaffected == 0) {
			return false;
		}
		return true;
	}
	public static boolean updateEmployeeAccount(EmployeeAccount employeeaccount) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		String[] sqlcolumns = new String[1];
		sqlcolumns[0] = "employee_id";
		String[] sqlvalues = new String[1];
		String sql;
		sqlvalues[0] = "" + employeeaccount.getEmployeeid();
		int rowsaffected;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		if(dataconnection.exists("Employees", sqlcolumns, sqlvalues)) {
			return false;
		}
		sql = "UPDATE EmployeeAccounts"
				+ " SET "
						+ "username = '" + employeeaccount.getUsername() + "', "
						+ "password = '" + employeeaccount.getPassword() + "', "
						+ "first_name = '" + employeeaccount.getFirstname() + "', "
						+ "middle_name = '" + employeeaccount.getMiddlename() + "', "
						+ "last_name = '" + employeeaccount.getLastname() + "', "
						+ "email = '" + employeeaccount.getEmail() + "', "
						+ "address = '" + employeeaccount.getAddress() + "', "
						+ "city = '" + employeeaccount.getCity() + "', "
						+ "state = '" + employeeaccount.getState() + "', ";
						if(employeeaccount.isLocked() == true) {
							sql +=  "is_locked = 1, ";
						} else {
							sql += "is_locked = 0, ";
						}
						if(employeeaccount.isAdmin() == true) {
							sql +=  "is_admin = 1, ";
						} else {
							sql += "is_admin = 0, ";
						}
					 sql += "employee_id = " + employeeaccount.getEmployeeid()
				+ " WHERE "
						+ "employee_id = " + employeeaccount.getEmployeeid()
						+ ";";
 		rowsaffected = dataconnection.requestQuery(sql, "COMMIT;");		
		if(rowsaffected == 0) {
			return false;
		}
		return true;
	}
	public static EmployeeAccount getEmployeeAccount(String username, String password) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		EmployeeAccount employeeaccount = null;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery(
				"SELECT * FROM EmployeeAccounts"
				+ " WHERE username = '" + username + "'"
				+ " AND password = '" + password + "'"
				+ ";");
		while(resultset.next()) {
			employeeaccount = new EmployeeAccount();
			employeeaccount.setUsername(resultset.getString("username"));
			employeeaccount.setPassword(resultset.getString("password"));
			employeeaccount.setFirstname(resultset.getString("first_name"));
			employeeaccount.setMiddlename(resultset.getString("middle_name"));
			employeeaccount.setLastname(resultset.getString("last_name"));
			employeeaccount.setEmail(resultset.getString("email"));
			employeeaccount.setAddress(resultset.getString("address"));
			employeeaccount.setCity(resultset.getString("city"));
			employeeaccount.setState(resultset.getString("state"));
			if(resultset.getInt("is_locked") == 1) {
				employeeaccount.setLocked(true);
			} else {
				employeeaccount.setLocked(false);
			}
			if(resultset.getInt("is_admin") == 1) {
				employeeaccount.setAdmin(true);
			} else {
				employeeaccount.setAdmin(false);
			}
			employeeaccount.setEmployeeid(resultset.getInt("employee_id"));
		}
		CloserUtility.close(resultset);
		return employeeaccount;
	}
	public static ArrayList<String> pullEmployeeAccounts(String employeeid) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		ArrayList<String> employeeaccounts = new ArrayList<String>();
		EmployeeAccount employeeaccount;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery(
				"SELECT position_id FROM Employees"
				+ " WHERE employee_id = " + employeeid
				+ ";");
		if(resultset.next()) {
			if(resultset.getInt("position_id") < 3) {
				return employeeaccounts;
			}
		} else {
			return employeeaccounts;
		}
		resultset = null;
		resultset = dataconnection.requestQuery("SELECT * FROM EmployeeAccounts"
												+ " WHERE employee_id = (SELECT employee_id FROM Employees"
													+ " WHERE position_id < (SELECT position_id FROM Employees"
														+ " WHERE employee_id = " + employeeid + "))"
												+ ";");		
		while(resultset.next()) {
			employeeaccount = new EmployeeAccount();
			employeeaccount.setUsername(resultset.getString("username"));
			employeeaccount.setPassword(resultset.getString("password"));
			employeeaccount.setFirstname(resultset.getString("first_name"));
			employeeaccount.setMiddlename(resultset.getString("middle_name"));
			employeeaccount.setLastname(resultset.getString("last_name"));
			employeeaccount.setEmail(resultset.getString("email"));
			employeeaccount.setAddress(resultset.getString("address"));
			employeeaccount.setCity(resultset.getString("city"));
			employeeaccount.setState(resultset.getString("state"));
			if(resultset.getInt("is_locked") == 1) {
				employeeaccount.setLocked(true);
			} else {
				employeeaccount.setLocked(false);
			}
			if(resultset.getInt("is_admin") == 1) {
				employeeaccount.setAdmin(true);
			} else {
				employeeaccount.setAdmin(false);
			}
			employeeaccount.setEmployeeid(resultset.getInt("employee_id"));
			employeeaccounts.add(employeeaccount.toJSON());
		}
		CloserUtility.close(resultset);
		return employeeaccounts;
	}
}
