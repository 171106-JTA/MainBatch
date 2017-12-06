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

public class SystemDAOImpl implements SystemDAO{
	
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
	public boolean submitRequest(ReimburseRequest request) {
		String sql = "{call submit_request(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			cs = conn.prepareCall(sql);
			cs.setInt(1, request.getEventType());
			cs.setInt(2, request.getEmpID());
			cs.setString(3, request.getFname());
			cs.setString(4, request.getLname());
			cs.setTimestamp(5, Timestamp.valueOf(request.getEventDate()));
			cs.setString(6, request.getLocation());
			cs.setString(7, request.getDescription());
			cs.setDouble(8, request.getCost());
			cs.setInt(9, request.getGradingFormat());
			cs.setString(10, request.getJustification());
			cs.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			close(cs);
		}
		
		return true;
	}
	
	@Override
	public List<ReimburseRequest> getEmployeeRequests(int empId) {
		List<ReimburseRequest> rrList = new ArrayList<>();
		ReimburseRequest rr = null;
		String sql = "SELECT * FROM All_Request_Data WHERE EID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rr = new ReimburseRequest(rs.getInt("RRID"), rs.getDouble("PRICE"), rs.getString("EVENTNAME"), rs.getDate("EVENT_DATETIME").toLocalDate());
				rrList.add(rr);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close(ps);
		}
		
		if(rrList.isEmpty()) {
			return null;
		}
		
		return rrList;
	}
}
