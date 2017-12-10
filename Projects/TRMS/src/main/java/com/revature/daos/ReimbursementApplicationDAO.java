package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.ReimbursementApplication;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class ReimbursementApplicationDAO {
	public static boolean insertReimbursementApplication(ReimbursementApplication reimbursementapplication) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		String[] sqlcolumns = new String[1];
		sqlcolumns[0] = "application_id";
		String[] sqlvalues = new String[1];
		sqlvalues[0] = "" + reimbursementapplication.getApplicationid();
		String sql;
		int rowsaffected;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		while(dataconnection.exists("ReimbursementApplications", sqlcolumns, sqlvalues)) {
			if((reimbursementapplication.getApplicationid() + 1) <= 99999999) {
				reimbursementapplication.setApplicationid(reimbursementapplication.getApplicationid() + 1);
			} else {
				reimbursementapplication.setApplicationid(reimbursementapplication.getApplicationid() - 3);
			}
		}
		while(!dataconnection.initializeConnection()) {
		}
		sql = "INSERT INTO ReimbursementApplications(application_id, employee_id, status_id, event_id, amount, is_passed, is_approved)"
				+ " VALUES("
					+ reimbursementapplication.getApplicationid() + ", "
					+ reimbursementapplication.getEmployeeid() + ", "
					+ reimbursementapplication.getStatusid() + ", "
					+ reimbursementapplication.getEventid() + ", "
					+ reimbursementapplication.getAmount() + ", ";
					if(reimbursementapplication.isPassed() == 1) {
						sql += "1, ";
					} else {
						sql += "0, ";
					}
					if(reimbursementapplication.isApproved() == 1) {
						sql += "1 ";
					} else {
						sql += "0";
					}
					sql += ")";
		rowsaffected = dataconnection.requestQuery(sql, "COMMIT");
		if(rowsaffected == 0) {
			return false;
		}
		return true;
	}
	public static ArrayList<String> getReimbursementApplications(int employeeid) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		ArrayList<String> reimbursementapplications = new ArrayList<String>();
		ReimbursementApplication reimbursementapplication;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery(
				"SELECT * FROM ReimbursementApplications"
				+ " WHERE employee_id = " + employeeid);
		while(resultset.next()) {
			reimbursementapplication = new ReimbursementApplication();
			reimbursementapplication.setApplicationid(resultset.getInt("application_id"));
			reimbursementapplication.setEmployeeid(resultset.getInt("employee_id"));			
			reimbursementapplication.setStatusid(resultset.getInt("status_id"));
			reimbursementapplication.setAmount(resultset.getFloat("amount"));
			reimbursementapplication.setEventid(resultset.getInt("event_id"));
			reimbursementapplication.setApproved(resultset.getInt("is_approved"));
			reimbursementapplications.add(reimbursementapplication.toJSON()); 
		}
		CloserUtility.close(resultset);
		return reimbursementapplications;
	}
	public static ArrayList<String> pullReimbursementApplications(String employeeid) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		ArrayList<String> reimbursementapplications = new ArrayList<String>();
		ReimbursementApplication reimbursementapplication;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery(
				"SELECT * FROM ReimbursementApplications"
				+ " WHERE status_id = (SELECT status_id FROM ApprovalStatuses"
					+ " WHERE handler_position = (SELECT position_id FROM Employees"
						+ " WHERE employee_id = " + employeeid + ")) AND is_passed = 0 AND is_approved = 0");
		while(resultset.next()) {
			reimbursementapplication = new ReimbursementApplication();
			reimbursementapplication.setApplicationid(resultset.getInt("application_id"));
			reimbursementapplication.setEmployeeid(resultset.getInt("employee_id"));			
			reimbursementapplication.setStatusid(resultset.getInt("status_id"));
			reimbursementapplication.setAmount(resultset.getFloat("amount"));
			reimbursementapplication.setEventid(resultset.getInt("event_id"));
			reimbursementapplication.setPassed(resultset.getInt("is_passed"));
			reimbursementapplication.setApproved(resultset.getInt("is_approved"));
			reimbursementapplications.add(reimbursementapplication.toJSON()); 
		}
		CloserUtility.close(resultset);
		return reimbursementapplications;
	}
	public static boolean updateReimbursementApplication(ReimbursementApplication reimbursementapplication) throws SQLException, IOException {
		DataConnectionUtility dataconnection;
		String sql;
		int rowsaffected;
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		sql = "UPDATE  ReimbursementApplications"
				+ " SET "
				+ "status_id = " + reimbursementapplication.getStatusid() + ", "
				+ "event_id = " + reimbursementapplication.getEventid() + ", "
				+ "amount = " + reimbursementapplication.getAmount() + ", ";
				if(reimbursementapplication.isPassed() == 1) {
					sql += "is_passed = 1, ";
				} else {
					sql += "is_passed = 0, ";
				}
				if(reimbursementapplication.isApproved() == 1) {
					sql += "is_approved = 1";
				} else {
					sql += "is_approved = 0";
				}
				sql += " WHERE "
					+ "application_id = " + reimbursementapplication.getApplicationid()
					+ " AND "
					+ "employee_id = " + reimbursementapplication.getEmployeeid();
		rowsaffected = dataconnection.requestQuery(sql, "COMMIT");
		if(rowsaffected == 0) {
			return false;
		}
		return true;
	}
}
