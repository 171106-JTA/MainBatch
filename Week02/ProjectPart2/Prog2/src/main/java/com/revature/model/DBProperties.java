package com.revature.model;

public class DBProperties {

	// Authentication
	public static final String URL = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:xe"; 
	public static final String USER = "Sean";
	public static final String PASS = "sean1234";

	// No hard code values
	public static final String DOT = ".";
	
	// Account tables
	public static final String ACCT_TAB = "Account";
	public static final String PERM_TAB = "Permissions";
	public static final String ACCT_TYPE_TAB = "Account_Type";
	public static final String CREDENTIALS_TAB = "Credentials";
	
	// User tables
	public static final String USER_TAB = "User";
	public static final String SSN_TAB = "SSN";
	public static final String State_TAB = "State";
	public static final String Status_TAB = "Status";
	public static final String Type_TAB = "User_Type";
	public static final String AcctVar_TAB = "Account_Variance";
	public static final String LOCK_TAB = "Locked_User";
	
	// Admin tables
	public static final String ADMIN_TAB = "Admin";
	public static final String ADMIN_Type_TAB = "Admin_Type";
	
	// Request tables
	public static final String REQ_TAB = "Request";
	public static final String REQ_TYPE_TAB = "Request_Type";
	
	//Adding Transaction Requests
	public static final String TRANS_TAB = "Transaction";
	public static final String TRANS_TYPE_TAB = "Transaction_Type";
	
	
	// Loans
	public static final String LOAN_TAB = "Loan";
	
	// Rollback/loan requests
	public static final String UserAcctJunction_TAB = "User_Account_Junction";
	public static final String AdminAcctJunction_TAB = "Admin_Account_Junction";
	public static final String UserTransJunction_TAB = "User_Transaction_Junction";
	public static final String UserReqJunction_TAB = "User_Request_Junction";
	public static final String AdminUserJunction_TAB = "Admin_User_Junction";
	public static final String ReqTransJunction_TAB = "Request_Transaction_Junction";
	public static final String LoanTransJUNCTION_TAB = "LOAN_TRANS_JUNCTION";
	
	// root sql directory
	public static final String ROOT_SQL = "./sql/";
	
	// Files
	public static final String FETCH = "/fetch.sql";
	public static final String INSERT = "/insert.sql";
	public static final String UPDATE = "/update.sql";
	public static final String DELETE = "/delete.sql";
	
	// Codes
	public static final int DB_ADM = 0;
	public static final int SITE_ADM = 1;
	
//	//Keys
//	public static final String PK = "PK_";
//	public static final String FK = "FK_";
//	public static final String Acct_PK = "Account_ID";
//	public static final String User_PK = "User_ID";
//	public static final String UserType_PK = "User_Type_ID";
//	public static final String Admin_PK = "Admin_ID";
//	public static final String AdminType_PK = "Admin_Type_ID";
//	public static final String Stat_PK = "Status_ID";
//	public static final String State_PK = "State_ID";
//	public static final String Var_PK = "Variance_ID";
//	public static final String Req_PK = "Request_ID";
//	public static final String ReqType_PK = "Request_Type_ID";
//	public static final String Perm_PK = "Permissions_ID";
//	public static final String Perm_TYPE_PK = "Permissions_TYPE_ID";
//	public static final String Trans_PK = "Transaction_ID";	
//	public static final String TransType_PK = "Transaction_Type_ID";
//	public static final String Loan_PK = "Loan_ID";	
	
	
	
}
