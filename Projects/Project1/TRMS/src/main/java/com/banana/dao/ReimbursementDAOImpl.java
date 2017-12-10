package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.banana.bean.ReimburseRequest;
import com.banana.util.ConnectionUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO{
	
	/**
	 * This method creates a ReimburseRequest from database information
	 * A ReimburseRequest id is used to identify the request
	 * 
	 * @param rrId This parameter represents the id of the ReimburseRequest
	 * 
	 * @return ReimburseRequest object if the record is found, otherwise null
	 * 
	 */
	@Override
	public ReimburseRequest getSingleRequest(int rrId) {
		ReimburseRequest rr = null;
		String sql = "SELECT * FROM Reimbursement_Request WHERE RRID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rrId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rr = new ReimburseRequest(rrId, rs.getDouble("PRICE"), rs.getDate("EVENT_DATETIME").toLocalDate(), this.getStatus(rrId));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close(ps);
		}
		
		return rr;
	}
	
	/**
	 * This method creates a list of requests from an employee
	 * 
	 * @param empId This is used as the identifier to get requests
	 * 
	 * @return List<ReimburseRequest> list of request from an employee
	 * 
	 */
	@Override
	public List<ReimburseRequest> getEmployeeRequests(int empId) {
		List<ReimburseRequest> rrList = new ArrayList<>();
		ReimburseRequest rr = null;
		String sql = "SELECT * FROM All_Request_Data WHERE EID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rrId = 0;
		String status = "PENDING";
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rrId = rs.getInt("RRID");
				status = this.getStatus(rrId);
				rr = new ReimburseRequest(rrId, rs.getDouble("PRICE"), rs.getString("EVENTNAME"), rs.getDate("EVENT_DATETIME").toLocalDate(), status);
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
	
	/**
	 * This method gets all ReimburseRequests from the database for admins
	 * 
	 * @return List<ReimburseRequest> list of all reimbursement requests
	 * 
	 */
	@Override
	public List<ReimburseRequest> getAllRequests() {
		List<ReimburseRequest> rrList = new ArrayList<>();
		ReimburseRequest rr = null;
		String sql = "SELECT * FROM All_Request_Data";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rr = new ReimburseRequest(rs.getInt("RRID"), rs.getInt("EID"), rs.getString("FNAME"), rs.getString("LNAME"), 
						rs.getString("EVENT_PLACE"), rs.getString("EVENTNAME"), rs.getDate("EVENT_DATETIME").toLocalDate(), 
						rs.getDouble("PRICE"), rs.getString("JUSTIFY"), rs.getString("Description"),
						rs.getInt("DS_APPROVAL"), rs.getInt("DH_APPROVAL"), rs.getInt("BC_APPROVAL"));
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
	
	/**
	 * This method derives the status a request from 3 fields: 
	 * the direct supervisor signature, BenCo signature, 
	 * and direct head signature
	 * 
	 * @param rrId The id of the request record
	 * 
	 * @return the status of the request as a string
	 */
	private String getStatus(int rrId) {
		String status = "PENDING";
		String sql = "SELECT * FROM Reimburse_Request WHERE RRID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int benCo = 0;
		int deptHead = 0;
		int dirSup = 0;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rrId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				benCo = rs.getInt(14);
				deptHead = rs.getInt(12);
				dirSup = rs.getInt(12);
				
				if(benCo == -1 || deptHead == -1 || dirSup == -1) {
					status = "DENIED";
				}
				
				if(benCo > 0) {
					status = "APPROVED";
				}
			}
			
		}catch(SQLException e) {
			
		}finally {
			close(ps);
		}
		
		return status;
	}
}
