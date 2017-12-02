package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				emp = new Employee(rs.getString(8), rs.getString(9));
			}
		}catch(SQLException e) {
			
		}finally {
			close(ps);
		}
		
		return emp;
	}
	
	@Override
	public int submitRequest(ReimburseRequest request) {
		String sql = "{call submit_request(?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			cs = conn.prepareCall(sql);
			cs.setString(1, request.getLocation());
			cs.setString(2, request.getDescription());
			cs.setDouble(3, request.getCost());
			cs.setInt(4, request.getGradingFormat());
			cs.setString(5, request.getEventType());
			cs.setString(6, request.getJustification());
			cs.setInt(7, request.getEmpID());
			
			cs.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			close(cs);
		}
		
		return 1;
	}
}
