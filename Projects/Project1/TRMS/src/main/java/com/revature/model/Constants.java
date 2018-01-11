package com.revature.model;

public class Constants {

	private static final String configsDir = "/resources/config/";
	public static final String servletConfig = configsDir + "servlet.properties";
	
	public static final String userKey = "User";
	public static final String passKey = "Pass";
	
	public static final String passHash = "PASSWORD_HASH";
	public static final int AuthFail = -1;
	
	public static final String LoginFail = "Failed to Login. Username does not exist";
	public static final String SessionType = "SessionType";
	
	public static final String ToFile = "ToFile";
	public static final String FromFile = "FromFile";
	public static final int BasicSession = 0;
	public static final String RegFail = "Failed to register. Username already exists";
	public static final int RegSuccess = 0;
	public static final int MAX_PASS = 32;
	public static final int POOL_SIZE = 5;
	public static final String empId = "empId";
	public static final String eventId = "eventId";
	public static final String requestId = "reqId";
	public static final String departmentId = "deptId";
	public static final String mailId = "mailId";
	public static final String toId = "toId";	
	public static final String fromId = "fromId";
	public static final String superId = "superId";
	public static final Integer tuitionRequest = 0;
	public static final String MsgStr = "Hello,\nYou have a new request for employee: %s\n";
	public static final String AggroMsgStr = "Hello,\nA request has reached its max wait time under BenCo %s\n";
	public static final String messageBox = "messageBox";
	public static final String fileName = "fileName";
	public static final String empPattern = "employeeIds";
	public static final String grdPattern = "critKeys";
	public static final String pntPattern = "critVals";
	public static final String critType = "critType";
	public static final String evtType = "evtType";
	public static final String startD = "startDate";
	public static final String endD = "endDate";
	public static final int URGENT_TIME = 14;
	public static final String URGENT_STR = "---URGENT--- ";
	public static final Integer approved = 1;
	public static String subject = "subject";
	public static String urgentCheck = "urgent";
	public static String mainFile = "portal.html";
	public static String critId = "critId";

	// Caching strings
	// Monitor caching
	public static String inboxCache = "inboxCache";
	public static String requestsCache = "requestsCache";
	public static String eventsCache = "eventsCache";
	public static String employeesCache = "employeesCache";
	
	// click event caching
	public static String messageCache = "messageCache";
	public static String eventCache = "messageCache";
	public static String requestCache = "messageCache";
	public static String employeeCache = "messageCache";
	public static String criteriaCache = "messageCache";
	public static String salt = "SALT";

	public static String createdDate = "CREATED_DATE";
	public static String reportsTo = "REPORTS_TO";
	public static String reqID = "REQ_ID";
	public static String percentage = "PERCENTAGE";
	public static String empID = "EMP_ID";
	public static String executiveID;
	public static String eventID = "EVENT_ID";
	public static String status = "status";
	public static int MAX_AMOUNT = 1000;
	public static String requestAmount = "requestedAmount";
	public static String eventAmount = "eventAmount";
	public static String grading = "0";
}
