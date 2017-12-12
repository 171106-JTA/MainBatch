package com.revature.model.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.revature.model.Constants;
import com.revature.util.Dao;
import com.revature.util.JDBC;
import org.mindrot.jbcrypt.*;

public class EmployeeDao implements Dao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6045267861903978647L;

	private Collection<Map<String, String>> salts;
	
	private static final String empID = "Employee ID";
	private static final String employeeType = "Role";
	private static final String deptID = "Department ID";
	private static final String departmentType = "Department";
	private static final String supervisor = "Supervisor";
	private static final String acceptedAmount = "Accepted Amount";
	private static final String empStatus = "Status";

	private static final int ROUNDS = 10;
	// create signature: emptype, deptid, superiorid, username, password, salt
	private final String empCreateSP = "CALL CREATE_EMPLOYEE(?, ?, ?, ?, ?, ?)";

	private final String empCreateSPGetID = "CALL CREATE_EMPLOYEE_Get_Id(?, ?, ?, ?, ?, ?, ?)";

	// update signature: emp_id, emptype, deptid, superid, status, permissions,
	// amount, user, pass, salt
	private final String empUpdateSP = "CALL UPDATE_EMPLOYEE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// delete signature: emp_id
	private final String empDeleteSP = "CALL DELETE_EMPLOYEE(?)";

	private final static String fetchSubs = "SELECT EMP_ID, EMP_TYPE, DEPT_ID, DEPT_TYPE, REPORTS_TO, ACCEPTED_AMOUNT, EMP_STAT FROM EMPLOYEE NATURAL JOIN EMPLOYEE_TYPE NATURAL JOIN DEPARTMENT NATURAL "
			+ "JOIN DEPARTMENT_TYPE NATURAL JOIN EMPLOYEE_STATUS WHERE REPORTS_TO=%s AND EMP_ID != REPORTS_TO";

	private final String empFetch = "SELECT EMP_ID, EMP_TYPE, USERNAME, PASSWORD_HASH, SALT, REPORTS_TO, PERM_TYPE, ACCEPTED_AMOUNT, DEPT_TYPE, CITY, STATE, COUNTRY, ZIP  FROM EMPLOYEE \n"
			+ "    NATURAL JOIN EMPLOYEE_TYPE NATURAL JOIN EMPLOYEE_STATUS NATURAL JOIN PERMISSIONS NATURAL JOIN DEPARTMENT NATURAL JOIN DEPARTMENT_TYPE NATURAL JOIN DEPARTMENT_BRANCH\n"
			+ "    NATURAL JOIN CREDENTIALS";

	private final String fetchSuper = "SELECT REPORTS_TO FROM EMPLOYEE WHERE EMP_ID = ";

	private final static String credsFetch = "SELECT USERNAME, PASSWORD_HASH, SALT, EMP_TYPE_ID, EMP_ID FROM CREDENTIALS NATURAL JOIN EMPLOYEE WHERE USERNAME=";

	// private static final String saltFetch = "SELECT SALT FROM CREDENTIALS";

	// Create signature: toId, fromId, message, date created
	private final static String createMsgSP = "CALL CREATE_MESSAGE(?, ?, ?, ?, ?)";

	private final static String createReqMsgSP = "CALL CREATE_REQUEST_MESSAGE(?, ?, ?, ?, ?, ?)";

	private final static String fetchMessages = "SELECT MAIL_ID, TO_EMP, FROM_EMP, MAIL_SUBJECT, TIME_CREATED, MSG MAIL_STAT FROM INBOX WHERE TO_EMP=";

	private final static String fetchMessage = "SELECT MSG FROM INBOX WHERE MAIL_ID=";

	private static final String fetchAmount = "SELECT ACCEPTED_AMOUNT FROM EMPLOYEE WHERE EMP_ID=";

	private String username, password, empType, superId, reqId, empId, salt, empStat, empPerm, amount, deptId, fromId,
			msg, subj, created;

	// registration
	public EmployeeDao(String empType, String superId, String username, String password) {
		this.empType = empType;
		this.superId = superId;
		this.username = username;
		this.password = password;
	}

	// Create Message
	public EmployeeDao(String empId, String fromId, String subj, String msg, String created) {
		this.empId = empId;
		this.fromId = fromId;
		this.subj = subj;
		this.msg = msg;
		this.created = created;
	}

	// Create Request Message
	public EmployeeDao(String empId, String fromId, String reqId, String subj, String msg, String created) {
		this.empId = empId;
		this.fromId = fromId;
		this.reqId = reqId;
		this.subj = subj;
		this.msg = msg;
		this.created = created;
	}

	// fetch Credentials
	public EmployeeDao(String user, String pass) {
		this.username = user;
		this.password = pass;
	}

	// update
	public EmployeeDao(String empId, String username, String password, String empType, String superId, String empStat,
			String empPerm, String amount, String deptId, String salt) {
		this.empId = empId;
		this.empType = empType;
		this.deptId = deptId;
		this.superId = superId;
		this.empStat = empStat;
		this.empPerm = empPerm;
		this.amount = amount;
		this.username = username;
		this.password = password;
		this.salt = salt;
	}

	// delete
	public EmployeeDao(String empId) {
		this.empId = empId;
	}

	// fetch All
	public EmployeeDao() {
	}

	@Override
	public boolean insert(boolean atomic) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		// hash password
		String salt = generateSalt(atomic);
		String[] values = { empType, "-1", superId, username, BCrypt.hashpw(password, salt), salt };
		return JDBC.getInstance().executeProcedure(empCreateSP, atomic, values);
	}

	public Integer insertGetId(boolean atomic) throws IOException, SQLException {
		String salt = generateSalt(atomic);
		String[] values = { empType, "-1", superId, username, BCrypt.hashpw(password, salt), salt };
		return JDBC.getInstance().executeProcedureGetInt(empCreateSPGetID, atomic, values);
	}

	private String generateSalt(boolean atomic) {
		// byte[] ret = null;
		// if(salts == null) {
		// salts = JDBC.getInstance().fetch(saltFetch, atomic, false, null);
		// }
		return BCrypt.gensalt(ROUNDS);
	}

	public boolean insertMessage(boolean atomic) throws IOException {
		String[] values = { empId, fromId, subj, msg, created };
		return JDBC.getInstance().executeProcedure(createMsgSP, atomic, values);
	}

	public boolean insertReqMessage(boolean atomic) throws IOException {
		String[] values = { empId, fromId, reqId, subj, msg, created };
		return JDBC.getInstance().executeProcedure(createReqMsgSP, atomic, values);
	}

	@Override
	public boolean update(boolean atomic) throws IOException {
		String[] values = { empId, empType, deptId, superId, empStat, empPerm, amount, username, password, salt };
		return JDBC.getInstance().executeProcedure(empUpdateSP, atomic, values);
	}

	@Override
	public boolean delete(boolean atomic) throws IOException {
		String[] values = { empId };
		return JDBC.getInstance().executeProcedure(empDeleteSP, atomic, values);
	}

	@Override
	public Map<String, String> fetch(boolean atomic) {
		Iterator<Map<String, String>> iter;
		Collection<Map<String, String>> maps = JDBC.getInstance().fetch(empFetch, atomic);
		if (maps != null && !maps.isEmpty()) {
			iter = maps.iterator();
			if (iter.hasNext()) {
				return iter.next();
			}
		}
		return null;
	}
	
	public static Collection<Map<String, String>> fetchSubordinates(boolean atomic, String emp){
		return JDBC.getInstance().fetch(String.format(fetchSubs, emp), new String[]{empID, employeeType, deptID, departmentType, supervisor, acceptedAmount, empStatus}, atomic);
	}

	@Override
	public Collection<Map<String, String>> fetchAll(boolean atomic) {
		return JDBC.getInstance().fetch(empFetch, atomic);
	}

	public Map<String, String> fetchCreds(boolean atomic)
			throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String salt;
		String pass;
		Collection<Map<String, String>> maps;
		Map<String, String> map;
		String queryCreds = credsFetch + "'" + username + "'";

		maps = JDBC.getInstance().fetch(queryCreds, atomic);

		if (maps == null || maps.isEmpty())
			return null;

		map = maps.iterator().next();

		if (map.isEmpty())
			return null;

		// get salt
		salt = map.get(Constants.salt);
		if (salt == null)
			return null;

		pass = BCrypt.hashpw(password, salt);
		if (map.get(Constants.passHash) == null || pass == null || !map.get(Constants.passHash).equals(pass))
			return null;

		return map;
	}

	public Collection<Map<String, String>> fetchMessages(String fromId, boolean atomic) {
		return JDBC.getInstance().fetch(fetchMessages + "'" + fromId + "'", atomic);
	}

	public Map<String, String> fetchMessage(String mailId, boolean atomic) {
		Collection<Map<String, String>> maps;
		maps = JDBC.getInstance().fetch(fetchMessage + "'" + mailId + "'", atomic);
		if (maps == null || maps.isEmpty())
			return null;
		return maps.iterator().next();
	}

	public Collection<Map<String, String>> fetchSuper(String emp, boolean atomic) {
		return JDBC.getInstance().fetch(fetchSuper + "'" + emp + "'", atomic);
	}

	public static Collection<Map<String, String>> fetchAmount(String empId, boolean atomic) {
		return JDBC.getInstance().fetch(fetchAmount + "'" + empId + "'", atomic);
	}

}
