package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.trms.model.Employee;
import com.revature.trms.util.ConnectionUtil;

public class EmployeeDAO {
	public void insertNewEmployee(Employee emp) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO employee (employee_username, employee_password, employee_email, employee_fname, emplyee_lname"
				+ "VALUES(?,?,?,?,?)";
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getUsername());
			ps.setString(2, emp.getPassword());
			ps.setString(3, emp.getEmail());
			ps.setString(4, emp.getFname());
			ps.setString(5, emp.getLname());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}
	
	public Employee selectEmployeeByUsername(String string) {
		PreparedStatement ps = null;
		Employee emp = null;
		
		String sql = "SELECT * FROM employee WHERE employe_username = ?";
		
		try (Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return emp;
	}
	
	
	private void close(PreparedStatement ps) {
		// TODO Auto-generated method stub

	}

	public List<Employee> selectEmployeeByLikeUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
