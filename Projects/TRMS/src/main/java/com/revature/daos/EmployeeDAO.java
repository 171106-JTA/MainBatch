package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Employee;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class EmployeeDAO {
	public static boolean isEmployee(Employee employee) throws IOException, SQLException {
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery("SELECT * FROM Employees"
												+ " WHERE"
													+ " employee_id = " + employee.getEmployeeid() + " AND"
													+ " position_id = " + employee.getPositionid() + " AND"
													+ " reports_to = " + employee.getReportsto() + " AND"
													+ " facility_id = " + employee.getFacilityid());
		System.out.println(resultset);
		while(resultset.next()) {
			return true;
		}
		CloserUtility.close(resultset);
		return false;
	}
}
