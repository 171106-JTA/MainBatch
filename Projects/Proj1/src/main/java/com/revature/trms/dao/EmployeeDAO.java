package com.revature.trms.dao;

import static com.revature.trms.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.revature.trms.model.Employee;
import com.revature.trms.model.ReimbursementCase;
import com.revature.trms.util.ConnectionUtil;

public class EmployeeDAO {

	public void insertNewEmployee(Employee emp) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO employee (employee_username, employee_password, employee_email, employee_fname, employee_lname)"
				+ " VALUES(?,?,?,?,?)";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getUsername());
			ps.setString(2, emp.getPassword());
			ps.setString(3, emp.getEmail());
			ps.setString(4, emp.getFname());
			ps.setString(5, emp.getLname());
			int affected = ps.executeUpdate();
			System.out.println(affected + " rows updated.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	// Finds and selects a account by username and returns Employee Object
	public Employee selectEmployeeByUsername(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee emp = null;
		String sql = "SELECT * FROM employee WHERE employee_username = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username.toLowerCase());
			rs = ps.executeQuery();
			while (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getTime(7));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return emp;
	}

	// Finds and selects a account by user id and returns Employee Object
	public Employee selectEmployeeById(int empId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee emp = null;
		String sql = "SELECT * FROM employee WHERE employee_id = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			rs = ps.executeQuery();
			while (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getTime(7));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return emp;
	}

	public static void main(String[] args) {
		EmployeeDAO test = new EmployeeDAO();
		System.out.println("printing shit out");
		for (ReimbursementCase reim : test.getAllCases()) {

			System.out.println(reim);
		}
		EmployeeDAO test2 = new EmployeeDAO();
		for(Employee emp : test2.getAllEmployees()) {
			System.out.println(emp.getUsername());
		}
	}
	

	public List<Employee> getAllEmployees() {
		List<Employee> emps = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee emp = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Employee";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				emp = new Employee();
				emp.setUsername(rs.getString("EMPLOYEE_USERNAME"));
				// get employee object from userID
				emps.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emps;
	}

	public List<ReimbursementCase> getAllCases() {
		List<ReimbursementCase> reimbursemntCases = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReimbursementCase reimbursementCase = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT_CASE";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// get employee object from userID
				reimbursementCase = new ReimbursementCase();
				reimbursementCase.setCase_id(rs.getString(1));
				reimbursementCase.setEmployee(selectEmployeeById(rs.getInt("EMPLOYEE_ID")));
				reimbursementCase.setEvent_date(rs.getDate("EVENT_DATE"));
				reimbursementCase.setRequest_date(rs.getDate("REQUEST_DATE"));
				reimbursementCase.setDuration_days(rs.getInt("CASE_DURATION"));
				reimbursementCase.setLocation(rs.getString("CASE_LOCATION"));
				reimbursementCase.setDescription(rs.getString("CASE_DESCRIPTION"));
				reimbursementCase.setCost(rs.getDouble("CASE_COST"));
				reimbursementCase.setGradingformat(rs.getString("CASE_GRADING_FORMAT"));
				reimbursementCase.setEventType(rs.getString("CASE_EVENT_TYPE"));
				reimbursementCase.setAttachment(rs.getBytes("CASE_ATTACHMENT"));
				reimbursemntCases.add(reimbursementCase);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursemntCases;
	}

}
