package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.http.Part;

import com.banana.bean.ReimburseRequest;
import com.banana.util.ConnectionUtil;

public class UpdateDAOImpl implements UpdateDAO{
	
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
	public boolean updateRequestApproval(int roleId, int rrId, int decision) {
		String sql = null;
		
		if(roleId == 1) {
			sql = "UPDATE Reimbursement_Request SET DS_APPROVAL = ? WHERE RRID = ?";
		}
		else if(roleId == 2) {
			sql = "UPDATE Reimbursement_Request SET DH_APPROVAL = ? WHERE RRID = ?";
		}
		else if(roleId == 3) {
			sql = "UPDATE Reimbursement_Request SET BC_APPROVAL = ? WHERE RRID = ?";
		}
		else {
			return false;
		}
		System.out.println("here");
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, decision);
			ps.setInt(2, rrId);
			
			ps.executeQuery();
			
			commitChanges();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			close(ps);
		}
		return true;
	}
	
	@Override
	public void insertInfoRequest(int rrId, int empId) {
		String sql = "INSERT INTO Info_Request (RRID, REQUESTEEID) VALUES (?, ?)";
		PreparedStatement ps = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rrId);
			ps.setInt(2, empId);
			
			ps.executeQuery();
			commitChanges();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		
	}
	
	@Override
	public void updateInfoRequest(int irId, String additionInfo, Part file) {
		String sql = "Update Info_Request SET  ADDITIONAL_INFO = ?, MEDIA = ?, FILENAME = ?" +
				"WHERE irId = ?";
		PreparedStatement ps = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setString(1, additionInfo);
			try {
				ps.setBlob(2, file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps.setString(3, file.getName());
			ps.setInt(4,  irId);
			
			ps.executeQuery();
			commitChanges();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		
	}
	
	@Override
	public double getPercentage(int eventId) {
		String sql = "SELECT * FROM Event_Type WHERE EVENTID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		double percent = 0;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				percent = rs.getDouble(2);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		
		return percent;
	}
	
	public boolean setNewAmount(int empId, double newAmount) {
		String sql = "UPDATE Employee SET Avail_Amount = ? WHERE EID = ?";
		PreparedStatement ps = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, newAmount);
			ps.setInt(2, empId);
			
			ps.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			close(ps);
		}
		return true;
	}
	
	private void commitChanges() {
		Statement stmt = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "COMMIT";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
}
