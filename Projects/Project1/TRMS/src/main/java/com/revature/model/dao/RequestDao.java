package com.revature.model.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.util.Dao;
import com.revature.util.JDBC;

public class RequestDao implements Dao {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7820036838325034798L;
	
	private static final String reqID = "Request ID";
	private static final String requestType = "Request type";
	private static final String requestAmount = "Request Amount";
	private static final String eventID = "Event ID";
	private static final String empID = "Employee ID";
	private static final String supervisor = "Supervisor ID";
	private static final String reqStatus = "Status";

	private static final String fetchEmp = "SELECT EMP_ID FROM REQUEST_EMPLOYEE_JUNCTION WHERE REQ_ID=";

	private static final String fetchDates = "SELECT REQ_ID, EMP_ID, REPORTS_TO, CREATE_TIME, REQUESTED_AMOUNT FROM REQUEST"
			+ " NATURAL JOIN REQUEST_EMPLOYEE_JUNCTION NATURAL JOIN EMPLOYEE";
	
	// create signature: reqStat, reqType, message, evntId, empId
	private final String createSP = "CALL CREATE_REQUEST(?, ?, ?, ?, ?)";

	// create signature: reqStat, reqType, message, evntId, empId
	private final String createSPGetID = "CALL CREATE_REQUEST_GET_ID(?, ?, ?, ?, ?, ?)";

	// update signature: reqStat, percentage
	private static final String updateSP = "CALL UPDATE_REQUEST(?, ?, ?)";
	
	private static final String escalateSP = "CALL ESCALATE_REQUEST(?, ?, ?)";

	private static final String BENCO = "2";

	private static final String fetchPercent = "SELECT PERCENTAGE FROM REQUEST WHERE REQ_ID=";

	// Delte not implemented

	private final String fetch = "SELECT REQ_ID, REQ_TYPE, REQUESTED_AMOUNT, EVENT_ID, EMP_ID, REPORTS_TO, "
			+ "REQ_STAT FROM REQUEST NATURAL JOIN REQUEST_STATUS NATURAL JOIN "
			+ "REQUEST_TYPE NATURAL JOIN REQUEST_EMPLOYEE_JUNCTION NATURAL JOIN "
			+ "REQUEST_EVENT_JUNCTION NATURAL JOIN PERMISSIONS NATURAL JOIN EMPLOYEE "
			+ "NATURAL JOIN EMPLOYEE_TYPE";

	private final String whereClause = " WHERE EMP_ID=";
	
	private final String whereSuperClause = " WHERE REPORTS_TO=";

	private String reqId, reqStat, reqType, msg, ts, evntId, empId, reqAmount, supId;

	// Create Request
	public RequestDao(String reqType, String evntId, String empId, String ts) {
		this.reqType = reqType;
		this.evntId = evntId;
		this.empId = empId;
		this.ts = ts;
	}

	// Update Request
	public RequestDao(String reqId, String reqStat, String reqAmount) {
		this.reqId = reqId;
		this.reqStat = reqStat;
		this.reqAmount = reqAmount;
	}

	// Fetch all Reauests
	public RequestDao() {
	}

	// update 
	public RequestDao(String req, String stat, String reportsTo, String ts, String reqAmount) {
		this.reqId = req;
		this.reqStat = stat;
		this.supId = reportsTo;
		this.ts = ts;
		this.reqAmount = reqAmount;
	}

	@Override
	public boolean insert(boolean atomic) throws IOException {
		String[] values = { reqType, evntId, empId, ts };
		return JDBC.getInstance().executeProcedure(createSP, atomic, values);
	}

	public Integer insertGetId(boolean atomic) throws IOException, SQLException {
		String[] values = { reqId, reqStat, supId, ts, reqAmount };
		return JDBC.getInstance().executeProcedureGetInt(createSPGetID, atomic, values);
	}

	@Override
	public boolean update(boolean atomic) throws IOException {
		String[] values = { reqId, reqStat, reqAmount };
		return JDBC.getInstance().executeProcedure(updateSP, atomic, values);
	}
	

	public static boolean escalate(String reqId, String supId, String ts, boolean atomic) throws IOException {
		String[] values = { reqId, supId, ts };
		return JDBC.getInstance().executeProcedure(escalateSP, atomic, values);
	}

	@Override
	public boolean delete(boolean atomic) {
		// TODO Auto-generated method stub
		return false;
	}

	// fetch not implemented
	@Override
	public Map<String, String> fetch(boolean atomic) {
		return null;
	}

	public Collection<Map<String, String>> fetchAllRequests(boolean atomic, String... emps) {
		String queryStr = fetch;
		if (emps != null && emps.length > 0) {
			if(!emps[0].equals(BENCO))
			queryStr += whereClause + "'" + emps[0] + "'";
		}
		return JDBC.getInstance().fetch(queryStr, new String[]{reqID, requestType, requestAmount, eventID, empID, supervisor, reqStatus}, atomic);
	}

	// @Override
	public Collection<Map<String, String>> fetchAll(boolean atomic) {
		return null;
	}

	public static Collection<Map<String, String>> fetchEmpByRequest(String reqId, boolean atomic) {
		return JDBC.getInstance().fetch(fetchEmp + "'" + reqId + "'", atomic);
	}

	public static Collection<Map<String, String>> fetchDates() {
		return JDBC.getInstance().fetch(fetchDates, true);
	}

	public static Collection<Map<String, String>> fetchPercentage(String req, boolean atomic) {
		return JDBC.getInstance().fetch(fetchPercent + "'" + req + "'", atomic);
	}
}
