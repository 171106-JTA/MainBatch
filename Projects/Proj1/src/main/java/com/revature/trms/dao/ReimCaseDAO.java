package com.revature.trms.dao;

import static com.revature.trms.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.trms.model.Employee;
import com.revature.trms.model.ReimbursementCase;
import com.revature.trms.util.ConnectionUtil;

public class ReimCaseDAO {

	public List<ReimbursementCase> getAllCases() {
		List<ReimbursementCase> reimbursementCases = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReimbursementCase reimbursementCase = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT_CASE";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// get employee object from userID
				reimbursementCase = new ReimbursementCase();
				reimbursementCase.setCase_id(rs.getString(1));
				reimbursementCase.setEmployeeId(Integer.toString(rs.getInt("EMPLOYEE_ID")));
				reimbursementCase.setCase_status(rs.getInt("CASE_STATUS"));
				reimbursementCase.setEvent_date(rs.getDate("EVENT_DATE"));
				reimbursementCase.setRequest_date(rs.getDate("REQUEST_DATE"));
				reimbursementCase.setDuration_days(rs.getInt("CASE_DURATION"));
				reimbursementCase.setLocation(rs.getString("CASE_LOCATION"));
				reimbursementCase.setDescription(rs.getString("CASE_DESCRIPTION"));
				reimbursementCase.setCost(rs.getDouble("CASE_COST"));
				reimbursementCase.setGradingformat(rs.getString("CASE_GRADING_FORMAT"));
				reimbursementCase.setEventType(rs.getString("CASE_EVENT_TYPE"));
				reimbursementCase.setAttachment(rs.getBytes("CASE_ATTACHMENT"));

				reimbursementCases.add(reimbursementCase);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return reimbursementCases;
	}

	public List<ReimbursementCase> getAllCasesByUser(String username) {
		List<ReimbursementCase> reimCases = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReimbursementCase reimCase = null;
		EmployeeDAO empData = new EmployeeDAO();
		Employee emp = empData.selectEmployeeByUsername(username);
		int userId = emp.getUserId();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT_CASE WHERE EMPLOYEE_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				// get employee object from userID
				reimCase = new ReimbursementCase();
				reimCase.setCase_id(rs.getString(1));
				reimCase.setEmployeeId(Integer.toString(rs.getInt("EMPLOYEE_ID")));
				reimCase.setEvent_date(rs.getDate("EVENT_DATE"));
				reimCase.setRequest_date(rs.getDate("REQUEST_DATE"));
				reimCase.setCase_status(rs.getInt("CASE_STATUS"));
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
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return reimCases;
	}

	public boolean insertNewReimCase(ReimbursementCase reimCase) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO REIMBURSEMENT_CASE "
				+ "(EMPLOYEE_ID, EVENT_DATE, CASE_DURATION, CASE_LOCATION, CASE_DESCRIPTION, CASE_COST ,CASE_GRADING_FORMAT, CASE_EVENT_TYPE) "
				+ "VALUES(?,?,?,?,?,?,?,?)";
		// TODO: Fix this date
		java.util.Date utilDate = reimCase.getEvent_date();

		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		try (Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reimCase.getEmployeeId());
			ps.setDate(2, sqlDate);
			ps.setInt(3, reimCase.getDuration_days());
			ps.setString(4, reimCase.getLocation());
			ps.setString(5, reimCase.getDescription());
			ps.setDouble(6, reimCase.getCost());
			ps.setString(7, reimCase.getGradingformat());
			ps.setString(8, reimCase.getEventType());
			// add setBlob
			ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(ps);
		}

		return true;
	}

	public boolean updateCaseStatus(String approvalDecision, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userTypeStr = (String) session.getAttribute("usertype");
		int caseStatus = ReimbursementCase.caseStatus2Int(request.getParameter("status"));
		int userTypeNum = Employee.usertypeString2Int(userTypeStr);

		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = null;
			String sql = "UPDATE REIMBURSEMENT_CASE SET CASE_STATUS=? WHERE CASE_ID=?";
			ps = conn.prepareStatement(sql);
			if (approvalDecision.equals("Approve")) {
				if (userTypeNum == 3 && caseStatus <= 2) {
					ps.setInt(1, 2);
				} else if (userTypeNum == 2 && caseStatus <= 3) {
					ps.setInt(1, 3);
				} else if (userTypeNum == 1 && caseStatus <= 4) {
					ps.setInt(1, 4);
				}
			} else {
				ps.setInt(1, 5);
			}
			ps.setInt(2, Integer.parseInt(request.getParameter("caseId")));
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
