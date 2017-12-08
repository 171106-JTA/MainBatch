package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;
import com.banana.util.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO{
	
	@Override
	public Employee getEmployeeByUsername(String username) {
		Employee emp = null;
		String sql = "SELECT * FROM Employee WHERE USERNAME = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println(username);
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				emp = new Employee(rs.getString(8), rs.getString(9), rs.getInt(1), rs.getDouble(4), rs.getInt(2));
			}
		}catch(SQLException e) {
			
		}finally {
			close(ps);
		}
		
		return emp;
	}
	
	@Override
	public Employee getEmployeeById(int empId) {
		Employee emp = null;
		String sql = "SELECT * FROM Employee WHERE EID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				emp = new Employee(rs.getString(8), rs.getString(9), rs.getInt(1), rs.getDouble(4), rs.getInt(2));
			}
		}catch(SQLException e) {
			
		}finally {
			close(ps);
		}
		
		return emp;
	}
	
	@Override
	public int getEmployeeIdFromRequest(int rrId) {
		int empId = 0;
		String sql = "SELECT EID FROM All_Request_Data WHERE RRID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rrId);
			
			rs = ps.executeQuery();
			
			empId = Integer.parseInt(rs.getString("EID"));
		}catch(SQLException e) {
			
		}finally {
			close(ps);
		}
		
		return empId;
	}
}
