package com.project1.dao;

import static com.project1.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.project1.classes.Employee;
import com.project1.util.ConnectionUtil;

public class EmployeeDao {
	private Statement stmt = null;

	public void createEmp(Employee emp) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO employees " 
			+ "VALUES('" + emp.getEmployeeID() + "', '" + emp.getPassword() + "', '" + emp.getFname() + "', '"
						+ emp.getMname() + "', '" + emp.getLname() + "', '" + emp.getEmail() + "', '" + emp.getEmpPos() + "' )";
			stmt = conn.createStatement();
			ResultSet affected = stmt.executeQuery(sql);
			// System.out.println(affected);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}
	
	public int empExist(Employee emp) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT count(emp_id) "
					+ "FROM employees " + "WHERE emp_id = '" + emp.getEmployeeID() + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				// System.out.println(rs);
				count = Integer.parseInt(rs.getString("count(emp_id)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
		return count;
	}
	
	public Employee getEmp(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee currEmp = new Employee();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * "
					+ "FROM employees " + "WHERE emp_id = '" + id + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				// System.out.println(rs);
				currEmp.setEmployeeID(rs.getString("emp_id"));
				currEmp.setPassword(rs.getString("emp_pw"));
				currEmp.setFname(rs.getString("fname"));
				currEmp.setMname(rs.getString("mname"));
				currEmp.setLname(rs.getString("lname"));
				currEmp.setEmail(rs.getString("email"));
				currEmp.setEmpPos(Integer.parseInt(rs.getString("emp_pos")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
		return currEmp;
	}
}
