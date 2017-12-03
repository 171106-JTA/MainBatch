package com.revature.trms.dao;

import static com.revature.trms.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.model.ReimbursementCase;
import com.revature.trms.util.ConnectionUtil;

public class ReimCaseDAO {
	public List<ReimbursementCase> getAllCases() {
		List<ReimbursementCase> reimbursemntCases = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReimbursementCase reimbursementCase = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT_CASE";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			EmployeeDAO emp = new EmployeeDAO();
			while (rs.next()) {
				// get employee object from userID
				reimbursementCase = new ReimbursementCase();
				reimbursementCase.setCase_id(rs.getString(1));
				reimbursementCase.setEmployee(emp.selectEmployeeById(rs.getInt("EMPLOYEE_ID")));
				reimbursementCase.setEvent_date(rs.getDate("EVENT_DATE"));
				reimbursementCase.setRequest_date(rs.getDate("REQUEST_DATE"));
				reimbursementCase.setDuration_days(rs.getInt("CASE_DURATION"));
				reimbursementCase.setLocation(rs.getString("CASE_LOCATION"));
				reimbursementCase.setDescription(rs.getString("CASE_DESCRIPTION"));
				reimbursementCase.setCost(rs.getDouble("CASE_COST"));
				reimbursementCase.setGradingformat(rs.getString("CASE_GRADING_FORMAT"));
				reimbursementCase.setEventType(rs.getString("CASE_EVENT_TYPE"));
				reimbursementCase.setAttachment(rs.getBytes("CASE_ATTACHMENT"));
				reimbursemntCases.add(reimbursementCase);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return reimbursemntCases;
	}

	public List<ReimbursementCase> getAllCasesByUser(String username) {
		List<ReimbursementCase> reimCases = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReimbursementCase reimCase = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT_CASE WHERE EMPLOYEE_USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			EmployeeDAO emp = new EmployeeDAO();
			while (rs.next()) {
				// get employee object from userID
				reimCase = new ReimbursementCase();
				reimCase.setCase_id(rs.getString(1));
				reimCase.setEmployee(emp.selectEmployeeById(rs.getInt("EMPLOYEE_ID")));
				reimCase.setEvent_date(rs.getDate("EVENT_DATE"));
				reimCase.setRequest_date(rs.getDate("REQUEST_DATE"));
				reimCase.setDuration_days(rs.getInt("CASE_DURATION"));
				reimCase.setLocation(rs.getString("CASE_LOCATION"));
				reimCase.setDescription(rs.getString("CASE_DESCRIPTION"));
				reimCase.setCost(rs.getDouble("CASE_COST"));
				reimCase.setGradingformat(rs.getString("CASE_GRADING_FORMAT"));
				reimCase.setEventType(rs.getString("CASE_EVENT_TYPE"));
				reimCase.setAttachment(rs.getBytes("CASE_ATTACHMENT"));
				reimCases.add(reimCase);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return reimCases;
	}

	public void insertNewReimCase(ReimbursementCase reimCase) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO REIMBURSEMENT_CASE WHERE "
				+ "(EMPLOYEE_ID, EVENT_DATE, CASE_DURATION, CASE_LOCATION, CASE_DESCRIPTION, CASE_GRADING, CASE_EVENT_TYPE, CASE_ATTACHEMENT) "
				+ "VALUES(?,?,?,?,?,?,?)";
		try (Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reimCase.getEmployee().getUserId());
			ps.setDate(2, (Date) reimCase.getEvent_date());
			ps.setInt(3, reimCase.getDuration_days() );
			ps.setString(4, reimCase.getLocation());
			ps.setString(5, reimCase.getDescription());
			ps.setString(6, reimCase.getGradingformat());
			ps.setString(7, reimCase.getEventType());
			// add setBlob
			int affected = ps.executeUpdate();
			System.out.println(affected+ " rows updated");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

}
