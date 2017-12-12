package com.revature.model.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.revature.util.Dao;
import com.revature.util.JDBC;

public class EventDao implements Dao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6680854823936951153L;

	private static final String eventID = "Event ID";
	private static final String eventType = "Event Type";
	private static final String deptID = "Dept ID";
	private static final String deptType = "Dept";
	private static final String city = "City";
	private static final String state = "State";
	private static final String country = "Country";
	private static final String zip = "ZIP";
	private static final String eventAmount = "Amount";
	
	private static final String fetchEventStart = "SELECT START_DATE FROM EVENT WHERE EVENT_ID=";

	// create signature: emps, evnt_type, crit_type, deptId
	private final String createSP = "CALL CREATE_EVENT(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// create blob signature: critId, fName, fBlob
	private final String createBlobSP = "CALL INSERT_BLOB(?, ?, ?)";
	
	private final String whereClause = " WHERE EMP_ID=";

	private final String fetchGradingCriteria = "SELECT CRIT_TYPE, GRADE, THRESHOLD FROM CRITERIA NATURAL JOIN CRITERIA_TYPE \n"
			+ "NATURAL JOIN GRADING_CRITERIA_LOOKUP WHERE EVENT_ID=";
	
	private final String fetchBlobNames = "SELECT BLOB_ID, FNAME FROM EVENT_BLOB_LOOKUP WHERE EVENT_ID=";

	private final String fetchBlob = "SELECT FNAME, BLOB FROM EVENT_BLOB_LOOKUP WHERE BLOB_ID=";

	private final String fetchEvents = "SELECT EVENT_ID, EVENT_TYPE, EVENT_AMOUNT, DEPT_ID, DEPT_TYPE CITY, STATE, COUNTRY, ZIP"
			+ " FROM EVENT NATURAL JOIN DEPARTMENT NATURAL JOIN DEPARTMENT_TYPE NATURAL JOIN DEPARTMENT_BRANCH \n"
			+ "NATURAL JOIN EVENT_TYPE";
	
	private final static String fetchBlobsByEvent = "SELECT * FROM EVENT_BLOB_LOOKUP WHERE EVENT_ID=";

	
	private final static String blobCol = "fBlob";
	// update signature: not implemented
	// delete signature not implemented
	private String emps, evntType, critId, deptId, start, end, grades, points, evtAmount;

	// Create Event
	public EventDao(String emps, String evntType, String critId, String deptId, String start, String end, String grades,
			String points) {
		this.emps = emps;
		this.evntType = evntType;
		this.critId = critId;
		this.deptId = deptId;
		this.start = start.substring(0, start.indexOf("."));
		this.end = end.substring(0, start.indexOf("."));
		this.grades = grades;
		this.points = points;
	}

	// Fetch all events
	public EventDao() {
	}

	public EventDao(String emps, String evntType, String critId, String deptId, String start, String end, String grades,
			String points, String evtAmount) {
		this.emps = emps;
		this.evntType = evntType;
		this.critId = critId;
		this.deptId = deptId;
		this.start = start.substring(0, start.indexOf("."));
		this.end = end.substring(0, start.indexOf("."));
		this.grades = grades;
		this.points = points;
		this.evtAmount = evtAmount;
	}

	@Override
	public boolean insert(boolean atomic) throws IOException {
		String[] values = { emps, evntType, critId, deptId,  grades, points, start, end, evtAmount};
		return JDBC.getInstance().executeProcedure(createSP, atomic, values);
	}

	public boolean insertBlob(boolean atomic, String evntId, String path) throws IOException {
		String[] arr = path.split("/");
		String file = arr[arr.length - 1];
		String[] values = { evntId, file, path };
		return JDBC.getInstance().executeProcedureBlob(createBlobSP, atomic, values, 2, file);
	}

	@Override
	public boolean update(boolean atomic) {

		return false;
	}

	@Override
	public boolean delete(boolean atomic) {

		return false;
	}

	@Override
	public Map<String, String> fetch(boolean atomic) {
		return null;
	}

	@Override
	public Collection<Map<String, String>> fetchAll(boolean atomic) {
		return JDBC.getInstance().fetch(fetchEvents, new String[]{eventID, eventType, eventAmount, deptID, deptType, city, state, country, zip}, atomic);
	}

	public Collection<Map<String, String>> fetchEventStart(boolean atomic, String event) {
		return JDBC.getInstance().fetch(fetchEventStart + "'" + event + "'", atomic);
	}

	public Collection<Map<String, String>> fetchEventsByEmp(boolean atomic, String emp) {
		return JDBC.getInstance().fetch(fetchEvents + whereClause + "'" + emp + "'", atomic);
	}

	public Collection<Map<String, String>> fetchGradingCriteria(boolean atomic, String event) {
		return JDBC.getInstance().fetch(fetchGradingCriteria + "'" + event + "'", atomic);
	}

	public Collection<Map<String, String>> fetchBlobNames(boolean atomic, String event) {
		return JDBC.getInstance().fetch(fetchBlobNames + "'" + event + "'", atomic);
	}

	public Collection<Map<String, Object>> fetchBlobById(boolean atomic, String... blob) {
		return JDBC.getInstance().fetchBlob(fetchBlob + "'" + blob + "'", atomic, blobCol);
	}
	
//	public static Collection<Map<String, String>> fetchBlobs(boolean atomic){
//		return JDBC.getInstance().fetchBlob(fetchBlobsByEvent, atomic, blobCol);
//	}

}
