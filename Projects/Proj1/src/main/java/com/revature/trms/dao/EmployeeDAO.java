package com.revature.trms.dao;

import static com.revature.trms.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.model.Employee;
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
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getTime(7), rs.getInt(8), rs.getInt(9));
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
						rs.getString(6), rs.getTime(7), rs.getInt(8), rs.getInt(9));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return emp;
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
		} finally {
			close(ps);
		}
		return emps;
	}

	public void updateUserType(String username, String userType) {
		PreparedStatement ps = null;
		int usertypeNum = 4;
		if (userType.equals("admin")) {
			usertypeNum = 1;
		} else if (userType.equals("dept_head")) {
			usertypeNum = 2;
		} else if (userType.equals("supervisor")) {
			usertypeNum = 3;
		}

		String sql = "UPDATE EMPLOYEE SET EMP_CATEGORY=? WHERE EMPLOYEE_USERNAME=?";
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, usertypeNum);
			ps.setString(2, username);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public boolean updateUserSup(String username, int supervisorId) {
		PreparedStatement ps = null;
		String sql = "UPDATE EMPLOYEE SET SUPERVISOR=? WHERE EMPLOYEE_USERNAME=?";
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, supervisorId);
			ps.setString(2, username);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(ps);
		}
		return true;
	}

	public void updateUserTypeInCase(String username, int supervisorId) {
		PreparedStatement ps = null;
		EmployeeDAO ed = new EmployeeDAO();
		Employee emp = ed.selectEmployeeByUsername(username);
		String sql = "UPDATE EMPLOYEE SET SUPERVISOR_ID=? WHERE EMPLOYEE_ID=?";
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, supervisorId);
			ps.setInt(2, emp.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close(ps);
		}

	}

}
