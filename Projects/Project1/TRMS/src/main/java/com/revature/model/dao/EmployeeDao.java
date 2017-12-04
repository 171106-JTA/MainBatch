package com.revature.model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.google.gson.*;
import com.revature.model.Constants;
import com.revature.util.JDBC;

public class EmployeeDao implements Dao {

	private Random rand = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;

	// create signature: emptype, deptid, superiorid, username, password, salt
	private final String empCreateSP = "CALL: CREATE_EMPLOYEE(?, ?, ?, ?, ?, ?)";
	private final int createSPSize = 6;

	// update signature: emp_id, emptype, deptid, superid, status, permissions,
	// amount, user, pass, salt
	private final String empUpdateSP = "CALL: UPDATE_EMPLOYEE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final int updateSPSize = 10;

	// delete signature: emp_id
	private final String empDeleteSP = "CALL: DELETE_EMPLOYEE(?)";
	private final int deleteSPSize = 1;

	private final String empFetch = "SELECT EMP_ID, EMP_TYPE, USERNAME, PASSWORDS, SALT, REPORTS_TO, PERM_TYPE, ACCEPTED_AMOUNT, DEPT_TYPE, CITY, STATE, COUNTRY, ZIP  FROM EMPLOYEE \n"
			+ "    NATURAL JOIN EMPLOYEE_TYPE NATURAL JOIN EMPLOYEE_STATUS NATURAL JOIN PERMISSIONS NATURAL JOIN DEPARTMENT NATURAL JOIN DEPARTMENT_TYPE NATURAL JOIN DEPARTMENT_BRANCH\n"
			+ "    NATURAL JOIN CREDENTIALS";

	private final String credsFetch = "SELECT USERNAME, PASSWORD FROM CREDENTIALS WHERE USERNAME=?";

	private final String saltFetch = "SELECT SALT FROM CREDENTIALS";

	private String username, password, empType, superId, empId, salt, empStat, empPerm, amount, deptId;

	// registration
	public EmployeeDao(String empType, String superId, String username, String password, String deptId) {
		this.empType = empType;
		this.deptId = deptId;
		this.superId = superId;
		this.username = username;
		this.password = password;
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
	public EmployeeDao(String emp_id) {
		this.empId = empId;
	}

	@Override
	public boolean insert(boolean atomic) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		// hash password
		String pass;
		byte[] salt = generateSalt(atomic);
		pass = new String(hashPass(password, salt));
		String[] values = { empType, deptId, superId, username, pass, new String(salt) };
		return JDBC.getInstance().executeProcedure(empCreateSP, atomic, values, false, -1, null);
	}

	private byte[] generateSalt(boolean atomic) {
		int size = Constants.MAX_PASS - password.length();
		byte[] salt = new byte[size];
		rand.nextBytes(salt);
		while (JDBC.getInstance().fetch(saltFetch, atomic, false, -1).iterator().next().isEmpty()) {
			salt = new byte[16];
			rand.nextBytes(salt);
		}
		return salt;
	}

	private byte[] hashPass(String pass, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH * 8);

		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

		return f.generateSecret(spec).getEncoded();
	}

	@Override
	public boolean update(boolean atomic) throws IOException {
		String[] values = { empId, empType, deptId, superId, empStat, empPerm, amount, username, password, salt };
		return JDBC.getInstance().executeProcedure(empUpdateSP, atomic, values, false, -1, null);
	}

	@Override
	public boolean delete(boolean atomic) throws IOException {
		String[] values = { empId };
		return JDBC.getInstance().executeProcedure(empDeleteSP, atomic, values, false, -1, null);
	}

	@Override
	public Map<String, String> fetch(boolean atomic) {
		Collection<Map<String, String>> maps = JDBC.getInstance().fetch(empFetch, atomic, false, -1);
		if (maps != null && !maps.isEmpty() && maps.iterator().next() != null)
			return maps.iterator().next();
		return null;
	}

	@Override
	public Collection<Map<String, String>> fetchAll(boolean atomic) {
		return JDBC.getInstance().fetch(empFetch, atomic, false, -1);
	}

	public Map<String, String> fetchCreds(boolean atomic) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String salt;
		String pass;
		Collection<Map<String, String>> maps;
		Map<String, String> map;
		
		maps = JDBC.getInstance().fetch(credsFetch, atomic, false, -1);
		
		if(maps == null || maps.isEmpty())
			return null;
		
		map = maps.iterator().next();
		
		if(map.isEmpty())
			return null;
		
		// get salt
		salt = map.get("salt");
		if(salt == null)
			return null;
		
		pass = new String(hashPass(password, salt.getBytes()));
		
		if(map.get("passwords") == null || pass == null || !map.get("passwords").equals(pass))
			return null;
		
		return map;
	}

}
