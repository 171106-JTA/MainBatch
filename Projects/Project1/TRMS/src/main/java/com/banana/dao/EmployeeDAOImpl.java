package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.InfoRequest;
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
			while(rs.next()) {
				empId = Integer.parseInt(rs.getString("EID"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		
		return empId;
	}
	
	@Override
	public List<InfoRequest> getInfoRequests(int requesteeId) {
		List<InfoRequest> irList = new ArrayList<>();
		InfoRequest ir = null;
		String sql = "SELECT * FROM Info_Request WHERE REQUESTEEID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, requesteeId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ir = new InfoRequest(rs.getInt(1), rs.getInt(2));
				irList.add(ir);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close(ps);
		}
		
		if(irList.isEmpty()) {
			return null;
		}
		
		return irList;
	}
	
	@Override
	public List<InfoRequest> getAllInfoRequests() {
		List<InfoRequest> irList = new ArrayList<>();
		InfoRequest ir = null;
		String sql = "SELECT * FROM Info_Request";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ir = new InfoRequest(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBlob(6));
				irList.add(ir);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close(ps);
		}
		
		if(irList.isEmpty()) {
			return null;
		}
		
		return irList;
	}
}
