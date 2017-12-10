package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.util.ConnectionUtil;
import com.revature.beans.Attachment;
import com.revature.logging.LoggingService;

import oracle.jdbc.OracleTypes;

public class AttachmentDaoImpl {
	
	private ConnectionUtil cUtil;
	
	public AttachmentDaoImpl() {
		cUtil = ConnectionUtil.getInstance();
	}
	
public void addAttachment(Attachment a) throws SQLException {
		
		String sql = "{call insert_attachment(?,?,?,?)}";

		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			
			call.setString(1, a.getFilename());
			call.setString(2, a.getDirectory());
			call.setInt(3, a.getRequestId());
			call.setString(4, a.getApprovalType());
			
			call.executeUpdate();
			LoggingService.getLogger().info("Attachment for request " +
					a.getRequestId() + " added to database.");
		}
	}


	public List<Attachment> getAttachments() throws SQLException {
		
		List<Attachment> attachments = new ArrayList<Attachment>();
		
		String sql = "{call read_all_attachments(?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(1);
			
			while(rs.next()) {
				attachments.add(attachmentFromResultSet(rs));
			}
		}
		
		return attachments;
	}


	public List<Attachment> getEmployeeAttachments(int employeeId) throws SQLException {

		List<Attachment> attachments = new ArrayList<Attachment>();
		
		String sql = "{call read_attachments_by_employee(?, ?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, employeeId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			while(rs.next()) {
				attachments.add(attachmentFromResultSet(rs));
			}
		}
		
		return attachments;
	}


	public List<Attachment> getRequestAttachments(int requestId) throws SQLException {

		List<Attachment> attachments = new ArrayList<Attachment>();
		
		String sql = "{call read_attachments_by_request(?, ?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, requestId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			while(rs.next()) {
				attachments.add(attachmentFromResultSet(rs));
			}
		}
		
		return attachments;
	}


	public void modifyAttachment(int oldId, Attachment a) throws SQLException {

		if(a == null) {
			LoggingService.getLogger().info(
					"Failed to update null attachment with id " + oldId);
			return;
		}
		
		String sql = "{call update_attachment(?,?,?,?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, oldId);
			call.setString(2, a.getFilename());
			call.setString(3, a.getDirectory());
			call.setInt(4, a.getRequestId());
			call.setString(5, a.getApprovalType());
			
			call.executeUpdate();
			LoggingService.getLogger().info("Attachment " + oldId
					+ " modified.");
		}
	}


	public void deleteAttachment(int id) throws SQLException {
		
		String sql = "delete from attachment where attachment_id = ?";
		
		try(Connection conn = cUtil.getConnection()) {
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, id);
			p.executeUpdate();
			LoggingService.getLogger().info("Attachment " + id
					+ " deleted from database.");
		}
	}
	
	private Attachment attachmentFromResultSet(ResultSet rs) throws SQLException {
		Attachment a = new Attachment();
		a.setId(rs.getInt("attachment_id"));
		a.setFilename(rs.getString("attachment_filename"));
		a.setDirectory(rs.getString("attachment_directory"));
		a.setRequestId(rs.getInt("attachment_request_id"));
		a.setApprovalType(rs.getString("request_status_name"));
		return a;
	}

}
