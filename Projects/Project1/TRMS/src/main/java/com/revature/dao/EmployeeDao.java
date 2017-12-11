package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Employee;
import com.revature.log.LogUtil;
import com.revature.util.ConnectionUtil;



public class EmployeeDao {
	/*
	 * Inserts new employee from html form into DB table 'personnel'
	 */
	public void insertEmployee(Employee em) {
		PreparedStatement ps = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			LogUtil.log.info("JDBC class not found exception");		
		}

		try (Connection conn = ConnectionUtil.getConnection();) {
			System.out.println("connection made");
			String sql = "INSERT INTO personnel (fname, lname, usrnm, pswrd, email, phone, dept, amt_avail)"
						+ "VALUES(?,?,?,?,?,?,?,1000)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, em.getFname());
			ps.setString(2, em.getLname());
			ps.setString(3, em.getUsrnm());
			ps.setString(4, em.getPswrd());
			ps.setString(5, em.getEmail());
			ps.setString(6, em.getPhone());
			ps.setString(7, em.getDept());
			int affected = ps.executeUpdate();
			
			
			LogUtil.log.info(affected + " new employee successfully stored.");
		} catch (Exception e2) {
			LogUtil.log.info("New employee insert FAIL.");
			 e2.printStackTrace();
		} finally {
			close(ps);
		}
	}


	public Employee getEmployeeByUsername(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Employee em = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			LogUtil.log.info("JDBC class not found exception");		
		}
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM personnel WHERE usrnm = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username.toLowerCase());
			rs = ps.executeQuery();
			
			while(rs.next()){
				em = new Employee( 
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getInt(11),
						rs.getDouble(12)
						);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		return em;
	}

}
