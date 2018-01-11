package com.revature.model;

public class Properties {

	// Debug mode
	public static boolean DEBUG = false;
	
	// security key
	public static final String SECRET_KEY = "FC11863E245A98EE";

	// root admin
	public static final String ROOT_ADMIN_USER = "root";
	public static final String ROOT_ADMIN_PASS = "bobbert1";
	public static final int ROOT_USER_CRED_LIMIT = 0;
	public static final double ROOT_USER_BAL = 0.0;
	public static final double ROOT_USER_INT = 0.0;
	public static final int ROOT_USER_PENALTY = 0;	

	// basic interface strings
	public static final String LOGIN_REQ_USER = " User Login";
	public static final String LOGIN_REQ_ADMIN = " Admin Login";
	public static final String ROLLBACK_REQ = " Request a transaction rollback";
	public static final String REG_REQ = " Create account";
	public static final String QUIT_REQ = " Quit";
	public static final String TRANS_SUCC = "Transaction successful";
	public static final String LOAN_SUCC = "Loan paid off";
	public static final String REQ_SUCC = "Request submitted for review";
	public static final String CREATE_USER_QUERY = "Create username: ";
	public static final String CREATE_PASS_QUERY = " Create password: ";
	public static final String FILENAME = "model.ser";
	public static final String IVFILE = "iv.txt";

	public static final String USER_REQ = "Username: ";
	public static final String PASS_REQ = "Password: ";
	public static final String AGE_REQ = "Age: ";
	public static final String QUERY_STR = "What would you like to do?";
	public static final String LOGOFF_REQ = " Logoff";
	public static final String CANCEL_REQ = " Go back";

	public static final String LOGOUT_STR = "successfully logged out";

	// user strings
	public static final String ACCT_PENDING = "Account activation is pending permission from an admin";
	public static final String ACCT_LOCKED = "This account is locked. Please contact administrator and try again";
	public static final String ACCT_REJECTED = "Account request has been rejected. Please contact administrator and try again";
	public static final String ADMIN_PHONE = "202-555-0190";

	public static final String BALANCE_STR = "Current balance: ";
	public static final String AVAILABLE_CREDIT_STR = "Available credit: ";
	public static final String OUTSTANDING_CREDIT_STR = "Outstanding credit: ";
	public static final String INT_RATE = "Interest Rate: ";
	public static final String LOAN_REQ = " Request a loan";
	public static final String DEPO_REQ = " Make a deposit";
	public static final String ROLLBACK_QUERY_STR = " Enter transaction to request";
	public static final String WITHDRAW_REQ = " Make a withdrawal";
	public static final String AMOUNT_QUERY_STR = "Enter amount: ";
	public static final String MSG_QUERY_STR = "Enter a message: ";
	public static final String AMOUNT_CONFIRM_STR = "Confirm amount(y/n)? ";
	public static final String CLOSE_USER_REQ = " Close this account";
	
	public static final String ACCPTED_LOAN_MSG = " Loan accepted";
	public static final String REJECTED_LOAN_MSG = "Loan rejected";
	public static final String DEPO_ACCT = " Deposit to account";	
	public static final String DEPO_LOAN = "  Deposit to loan";	
	public static final String LOANS_STR = "  Outstanding loan(s)";	
	public static final String LOANS_QUERY_STR = "  Enter loan to pay";	
	public static final String CRED_LIM_STR = "Credit Available: ";
	public static final String CRED_INT_STR = "Interest Rate: ";
	public static final String SSN_REQ = "Social Security Number: ";



	// admin strings
	public static final String LOCK_REQ = " Lock user account(s)";
	public static final String LOCK_STR = "Unlocked account(s): ";
	public static final String LOCK_QUERY_STR = "Enter account(s) to lock: ";

	public static final String UNLOCK_REQ = " Unlock user account(s)";
	public static final String UNLOCK_STR = "Locked account(s):";
	public static final String UNLOCK_QUERY_STR = "Enter account(s) to unlock: ";

	public static final String VIEW_REQ = " View outstanding request(s)";
	public static final String ACCEPT_REQ = " Accept request";
	public static final String REJECT_REQ = " Reject request";
	
	public static final String DELIMIT_STR = "(delimit entries by space)";

	public static final String RESTORE_REQ = " Remove user(s) from reject list";
	public static final String VIEW_RESTORE_STR = "Rejected account(s): ";
	public static final String RESTORE_QUERY_STR = "Enter account(s) to remove: ";

	public static final String PROMOTE_REQ = " Promote user(s) to admin";
	public static final String VIEW_PROMOTE_STR = "Available account(s) to promote: ";
	public static final String PROMOTE_QUERY_STR = "Enter account(s) to promote: ";

	public static final String CLOSE_REQ = " Close user(s) account";
	public static final String VIEW_CLOSE_STR = "Available account(s) to close: ";
	public static final String CLOSE_QUERY_STR = "Enter account(s) to close: ";
	
	public static final String COUNT = "Approximate remaining: ";
	private static final String VIEW_USERS = " View user(s)";

	// page lists
	public static final String[] PAGE1 = { LOGIN_REQ_USER, LOGIN_REQ_ADMIN, REG_REQ, QUIT_REQ };
	public static final String[] PAGE2_USER = { LOAN_REQ, DEPO_REQ, WITHDRAW_REQ, ROLLBACK_REQ, CLOSE_USER_REQ, LOGOFF_REQ, QUIT_REQ };

	public static final String[] PAGE2_ADMIN = { LOCK_REQ, UNLOCK_REQ, VIEW_REQ, RESTORE_REQ, PROMOTE_REQ, CLOSE_REQ,
			VIEW_USERS, LOGOFF_REQ, QUIT_REQ };
	public static final String[] PAGE3_REQS = {ACCEPT_REQ, REJECT_REQ, CANCEL_REQ, QUIT_REQ};
	public static final String[] PAGE3_DEPO = {DEPO_ACCT, DEPO_LOAN, CANCEL_REQ, QUIT_REQ};


	// empty list
	public static final String EMPTY_LIST = "Empty";

	// ints
	public static final int SENIOR_CRED_LIMIT = 5000;
	public static final int BASIC_CRED_LIMIT = 3000;
	public static final int PREMIUM_CRED_LIMIT = 10000;
	public static final int SENIOR_MIN_AGE = 65;
	
	public static final int BASIC_USER_PENALTY = 50;
	public static final int PREMIUM_USER_PENALTY = 20;
	public static final int SENIOR_USER_PENALTY = 30;	
	
	public static final int SUCESSIVE_DAYS = 30;
	public static final int CRED_LIMIT_RATE = 200;
	
	public static final int PROT_GET = 0;
	public static final int PROT_ADD = 1;
	public static final int PROT_REMOVE = 2;
	public static final int PROT_CONVERT = 3;
	public static final int PROT_APPROVE = 4;
	public static final int PROT_REJECT = 5;
	public static final int PROT_POLL = 6;
	public static final int PROT_LOCK = 7;
	public static final int PROT_UNLOCK = 8;
	public static final int PROT_PROMOTE = 9;
	public static final int PROT_CLOSE = 10;
	public static final int PROT_CANCEL = 11;


	public static final int ACCT_STAT_GOOD = 0;
	public static final int ACCT_STAT_PENDING = 1;
	public static final int ACCT_STAT_LOCKED = 2;
	public static final int ACCT_STAT_REJECTED = 3;

	// doubles
	public static final double SENIOR_INT_RATE = 1.5;
	public static final double BASIC_INT_RATE_BELOW10 = 3.5;
	public static final double BASIC_INT_RATE_BELOW20 = 0.5;
	public static final double BASIC_INT_RATE_BELOW30 = 1;
	public static final double PREMIUM_INT_RATE = 1.0;
	public static final double INT_RATE_PER_MO = 0.5;

	// option strings
	public static final String OPTION_STR = "[y] yes [n] no [Y] yes to all [N] no to all";
	public static final String SINGLE_OPTION_STR = "(y/n)";
	public static final String CONFIRM_LOGOUT = "Confirm logout? " + SINGLE_OPTION_STR;
	public static final String CONFIRM_CLOSE = "Confirm account close? " + SINGLE_OPTION_STR;

	// chars
	public static final char READ = 'r';
	public static final char WRITE = 'w';
	public static final char Y = 'Y';
	public static final char N = 'N';
	public static final char y = 'y';
	public static final char n = 'n';

	// credentials
	public static final int MIN_SIZE_USER = 1;
	public static final int MAX_SIZE_USER = 100;
	public static final int MIN_SIZE_PASS = 1;
	public static final int MAX_SIZE_PASS = 100;
	public static final int MIN_LETTER_USER = 1;
	public static final int MIN_LETTER_PASS = 1;
	public static final int MIN_NUMBER_USER = 1;
	public static final int MIN_NUMBER_PASS = 1;
	public static final int MIN_SPEC_CHAR_USER = 0;
	public static final int MIN_SPEC_CHAR_PASS = 0;

	public static final String USER_CREDENTIALS = "User must be between " + MIN_SIZE_USER + " and " + MAX_SIZE_USER
			+ " and contain at least: " + MIN_LETTER_USER + " letter(s), " + MIN_NUMBER_USER + " number(s), and "
			+ MIN_SPEC_CHAR_USER + " special character(s) and no spaces";
	public static final String PASS_CREDENTIALS = "Password must be between " + MIN_SIZE_PASS + " and " + MAX_SIZE_PASS
			+ " and contain at least: " + MIN_LETTER_PASS + " letter(s), " + MIN_NUMBER_PASS + " number(s), and "
			+ MIN_SPEC_CHAR_PASS + " special character(s) and no spaces";

	// Long procedure processes
	public static final String DESERIALIZE = "Deserializing...";
	public static final String DECRYPT = "Decrypting(AES 128-bit/CBC/PKCS5Padding)...";
	public static final String QUIT = "Quitting...";
	public static final String ENCRYPT = "Encrypting(AES 128-bit/CBC/PKCS5Padding)...";
	public static final String SERIALIZE = "Serializing...";
	
	// error strings
	public static final String ERR_INPUT = "Invalid user input. Expected input between 0 and ";
	public static final String ERR_USER = "Invalid username: ";
	public static final String ERR_USER_NA = "Username already used: ";
	public static final String ERR_ILLEGAL_ADMIN = "Admins can not approve changes to their own user accounts";
	public static final String ERR_USER_ONLINE = "User alread logged on: ";
	public static final String ERR_ADMIN_DUP = "Admin already exists: ";
	public static final String ERR_ADMIN_CLOSE = "Admins cannot close admin accounts: ";
	public static final String ERR_REQ_DUP = "Request already exists: ";
	public static final String ERR_REQ_DNE = "Request does not exist: ";
	public static final String ERR_PASS = "Invalid password: ";
	public static final String ERR_LOAN_DNE = "Loan does not exist: ";
	public static final String ERR_LOAN_ACCEPTED = "Loan already accepted: ";
	public static final String ERR_ACCT_DNE = "Account does not exist: ";
	public static final String ERR_ACCT_ACCEPTED = "Account already activated: ";
	public static final String ERR_ACCT_OVERDRAFT = "Insufficient funds for request: ";
	public static final String ERR_ACCT_OVERDRAFT_PENALTY = "Overdraft penalty: ";
	public static final String ERR_NO_LINE = "No line found. Application forced to stop";	
	public static final String ERR_LOAN_DEBT = "Cannot close aacount while in debt: ";
	public static final String ERR_EXCESS_LOAN = "Can not close account with outstanding loans";
	public static final String ERR_RESTRICTED = "Admins cannot lock other admins: ";
	public static final String ERR_DESER = "Failed to deserialize. Forced to quit";
	public static final String ERR_SER = "Failed to serialize. Forced to quit";

	// warnings
	public static final Object DEBT_WARN = "After " + SUCESSIVE_DAYS + " consecutive days at negative balance account will be penalized:"
			+ "\n Interest increase: " + INT_RATE_PER_MO + "Credit penalty: " + CRED_LIMIT_RATE ;
}