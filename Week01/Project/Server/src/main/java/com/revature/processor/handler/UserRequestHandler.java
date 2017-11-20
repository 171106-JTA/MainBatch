package com.revature.processor.handler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.CodeList;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Status;
import com.revature.businessobject.info.account.Type;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.persistence.database.util.ConnectionUtil;
import com.revature.server.Server;
import com.revature.server.session.require.Require;

/**
 * Defines user account management process operations 
 * @author Antony Lulciuc
 */
public final class UserRequestHandler {
	private static List<CodeList> userCheckpoints;
	private static List<CodeList> accountStatus;
	private static List<CodeList> accountType;

	public UserRequestHandler() { 
		
	}
	
	
	/**
	 * Performs select query on database for user with specified username and password
	 * @param request request with username and password
	 * @return User BusinessObject if found else empty resultset 
	 */
	public Resultset login(Request request) throws RequestException {
		Resultset res = Server.database.select(BusinessClass.USER, request.getQuery());
		
		if (userCheckpoints == null)
			init();
		
		// If user found then validate checkpoint 
		if (res.size() > 0) {
			User user = (User) res.get(0);
			FieldParams params = new FieldParams();
			Resultset data;
			UserInfo info;
			
			params.put(UserInfo.USERID, Long.toString(user.getId()));
			data = Server.database.select(BusinessClass.USERINFO, params);
			
			if (data.size() > 0) {
				info = (UserInfo)data.get(0);
				user.setCheckpoint(getCodeListById(userCheckpoints, info.getStatusId()).getValue().toLowerCase());
			} else {
				user.setCheckpoint(Checkpoint.NONE);
			}
			
			Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER, 
					Checkpoint.PENDING, Checkpoint.NONE }, user.getCheckpoint(), request);
		}
		
		return res;
	}
	
	///
	//	USER
	///
	
	/**
	 * Creates new user accounts 
	 * @param request account information
	 * @return modified record count should equal 1
	 */
	public Resultset createUser(Request request) throws RequestException {
		FieldParams trans = request.getTransaction();
		int total = 0;
		
		total = Server.database.insert(BusinessClass.USER, trans);
		
		return new Resultset(total);
	}
	
	/**
	 * Deletes user and all data associated with them
	 * @param request - must define query for admin accounts 
	 * @return total number of records removed 
	 */
	public Resultset deleteUser(Request request) {
		FieldParams info = new FieldParams();
		FieldParams accounts = new FieldParams();
		FieldParams userdata = new FieldParams();
		Resultset res;
		Account acct;
		String userid;
		int total;
		
		// If admin then allowed to remove other accounts 
		if (request.getCheckpoint().equals(Checkpoint.ADMIN)) {
			FieldParams query = request.getQuery();
			userid = query.get(User.ID);
		} else {
			userid = Long.toString(request.getUserId());
		}
		
		// set parameters needed to remove user data
		userdata.put(User.ID, userid);
		info.put(Info.USERID, userid);
		
		// get accounts 
		res = Server.database.select(BusinessClass.ACCOUNT, info);
		
		// If content found delete all account data
		if (res.size() > 0) {
			for (BusinessObject object : res) {
				acct = (Account)object;
				accounts.put(Account.NUMBER, acct.getNumber());
				
				// delete all accounts
				Server.database.delete(BusinessClass.CHECKING, accounts);
				Server.database.delete(BusinessClass.CREDIT, accounts);
				Server.database.delete(BusinessClass.ACCOUNT, accounts);
			}
		}
		
		
		// Remove all records associated with this id
		total = Server.database.delete(BusinessClass.USERINFO, info);
		total += Server.database.delete(BusinessClass.USER, userdata);
		
		return new Resultset(total);
	}
	
	/**
	 * Performs query of User records 
	 * @param request conditions users must have to be in resultset
	 * @return 0 to n user records
	 */
	public Resultset getUser(Request request) throws RequestException {
		FieldParams query = request.getQuery();
		FieldParams params = new FieldParams();
		Resultset data;
		UserInfo info;
		Resultset res;
		CodeList codelist;
		User user;
		
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
		
		if (query.get(User.CHECKPOINT) != null) {
			res = new Resultset();
			codelist = getCodeListByValue(userCheckpoints, query.get(User.CHECKPOINT));
			params.put(UserInfo.STATUSID, Long.toString(codelist.getId()));
			data = Server.database.select(BusinessClass.USERINFO, params);
			
			for (BusinessObject object : data) {
				params.clear();
				params.put(User.ID, Long.toString(((UserInfo)object).getUserId()));
				res.addAll(Server.database.select(BusinessClass.USER, params));
			}
		} else {
			res = Server.database.select(BusinessClass.USER, request.getQuery());
		}
		
		for (BusinessObject item : res) {
			params.clear();
			
			user = (User)item;
			params.put(UserInfo.USERID, Long.toString(user.getId()));
			data = Server.database.select(BusinessClass.USERINFO, params);
			
			if (data.size() > 0) {
				info = (UserInfo)data.get(0);
				user.setCheckpoint(getCodeListById(userCheckpoints, info.getStatusId()).getValue().toLowerCase());
			} else {
				user.setCheckpoint(Checkpoint.PENDING);
			}	
		}
		
		return res;
	}
	
	/**
	 * Allows everything except user id to be updated 
	 * @param request - query and transaction must be defined 
	 * @return total number of records modified 
	 * @throws RequestException
	 */
	public Resultset setUser(Request request) throws RequestException {
		FieldParams query = request.getQuery();
		FieldParams trans = request.getTransaction();
		FieldParams verify;
		
		// If User name being updated 
		if (trans.get(User.USERNAME) != null) {
			verify = new FieldParams();
			
			// get data to validate
			verify.put(User.USERNAME, trans.get(User.USERNAME));
			
			// Ensure that update is unique
			Require.requireUnique(BusinessClass.USER, verify, request);
		}
		
		// If user account Ensure they are updating their account only 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) {
			Require.requireSelfQuery(request);
		} 
		
		trans.remove(User.CHECKPOINT);
		
		// Ensure we do not change user id
		trans.remove(User.ID);
		
		// Perform update
		return new Resultset(Server.database.update(BusinessClass.USER, query, trans));
	}
	
	///
	//	USERINFO
	///
	
	/**
	 * Generates UserInfo record
	 * @param request - transaction must be defined 
	 * @return modified record count should equal 1
	 * @throws RequestException
	 */
	public Resultset createUserInfo(Request request) throws RequestException {
		FieldParams loc = new FieldParams();
		FieldParams trans = request.getTransaction();
		Resultset res;
		CodeList item;
		
		loc.put(CodeList.CODE, "CITY-CODE-GROUP");
		loc.put(CodeList.VALUE, trans.get(UserInfo.STATE));
		loc.put(CodeList.DESCRIPTION, trans.get(UserInfo.CITY));
		res = Server.database.select(BusinessClass.CODELIST, loc);
		
		if (res.size() == 0)
			return null;
		
		item = (CodeList)res.get(0);
		trans.put(UserInfo.STATECITYID, Long.toString(item.getId()));
		trans.remove(UserInfo.STATE);
		trans.remove(UserInfo.CITY);
		
		for (CodeList record : userCheckpoints) {
			if ("pending".equals(record.getValue().toLowerCase())) {
				trans.put(UserInfo.STATUSID, Long.toString(record.getId()));
				break;
			}
		}
		
		return new Resultset(Server.database.insert(BusinessClass.USERINFO, trans));
	}
	
	/**
	 * Acquires UserInfo records
	 * @param request - query must be defined 
	 * @return 0 to n userinfo records
	 * @throws RequestException
	 */
	public Resultset getUserInfo(Request request) throws RequestException { 
		Resultset res;
		UserInfo info;
		CodeList item;
		
		// For NON-ADMINS only personal account information should be accessible 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) 
			Require.requireSelfQuery(request);
		
		res = Server.database.select(BusinessClass.USERINFO, request.getQuery());
		
		for (BusinessObject object : res) {
			info = (UserInfo)object;
			item = getCodeListById(userCheckpoints, info.getStatusId());
			info.setStatus(item.getValue());
		}
		
		return res;
	}
	
	/**
	 * Updates user info (contact data) 
	 * @param request - query and transaction must be defined
	 * @return number of required updated
	 * @throws RequestException
	 */
	public Resultset setUserInfo(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		FieldParams query = request.getQuery();
		
		// For NON-ADMINS only personal account information should be accessible 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) {
			Require.requireSelfQuery(request);
			
			// auto-set to find self
			query = new FieldParams();
			query.put(Info.USERID, Long.toString(request.getUserId()));
		}
		
		// We are not allowed to change the userid
		transact.remove(Info.USERID);
		
		if (transact.get(UserInfo.STATUSID) != null) {
			CodeList item = getCodeListByValue(userCheckpoints, transact.get(UserInfo.STATUSID));
			transact.put(UserInfo.STATUSID, Long.toString(item.getId()));
		}
		
		return new Resultset(Server.database.update(BusinessClass.USERINFO, query, transact));
	}
	
	///
	//	ACCOUNT
	///
	
	/**
	 * Creates new account (sets status to PENDING [ALWAYS]) using user id on session as foreign key.
	 * @param request - transaction must be defined 
	 * @param type
	 * @return number of records created, should be 1 on success else 0
	 * @throws RequestException
	 */
	public Resultset createAccount(Request request, String type) throws RequestException {
		CallableStatement stat = null;
		String sql = "{call create_account(?,?,?,?)}";
		int total = 0;
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			stat = conn.prepareCall(sql);
			stat.setLong(1, request.getUserId());
			stat.setString(2,  Status.PENDING);
			stat.setString(3, "checking");
			stat.setString(4, null);
			stat.executeQuery();
			total = 1;
		} catch (SQLException e) {
			
		} finally {
			ConnectionUtil.close(stat);
		}
		
		return new Resultset(total);
	}
	
	/**
	 * Removes specified account records from system
	 * @param request - must have query defined 
	 * @return number of records deleted
	 * @throws RequestException
	 */
	public Resultset deleteAccount(Request request) throws RequestException {
		FieldParams params = new FieldParams();
		int total = 0;
		// If non-admin account, then we should be deleting own account ONLY
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
			
		params.put(Account.NUMBER, request.getQuery().get(Account.NUMBER));
		total = Server.database.delete(BusinessClass.CHECKING, params);
		total += Server.database.delete(BusinessClass.CREDIT, params);
		total += Server.database.delete(BusinessClass.ACCOUNT, request.getQuery());
		
		return new Resultset(total);
	}
	
	/**
	 * Performs query to database for user Accounts 
	 * @param request - query must be defined 
	 * @return 0 to n account records
	 * @throws RequestException
	 */
	public Resultset getAccount(Request request) throws RequestException {
		FieldParams query = request.getQuery();
		FieldParams params = new FieldParams();
		Resultset data;
		Account acct;
		Resultset res;
		CodeList codelist;
		Resultset accounts;
		
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
		
		if (query.get(Account.STATUSID) != null) {
			res = new Resultset();
			codelist = getCodeListByValue(accountStatus, query.get(Account.STATUSID));
			params.put(Account.STATUSID, Long.toString(codelist.getId()));
			data = Server.database.select(BusinessClass.ACCOUNT, params);
			
			for (BusinessObject object : data) {
				params.clear();
				params.put(Account.USERID, Long.toString(((Account)object).getUserId()));
				res.addAll(accounts = Server.database.select(BusinessClass.ACCOUNT, params));
				
				for (BusinessObject account : accounts) {
					codelist = getCodeListById(accountType, ((Account)account).getTypeId());
					((Account)account).setType("checking");
				}
			}
		} else {
			res = Server.database.select(BusinessClass.ACCOUNT, request.getQuery());
		}
		
		for (BusinessObject item : res) {
			params.clear();
			
			acct = (Account)item;
			params.put(Account.STATUSID, Long.toString(acct.getStatusId()));
			codelist = getCodeListById(accountStatus, acct.getStatusId());
			acct.setStatus(codelist.getValue());
			acct.setType("checking");
		}
		
		return res;
	}
	
	/**
	 * Performs query to database for user Accounts 
	 * @param request - must define query
	 * @param type  - type of account to search for
	 * @return 0 to n account records
	 * @see Type
	 * @throws RequestException
	 */
	public Resultset getAccount(Request request, String type) throws RequestException {
		FieldParams query = request.getQuery();
		FieldParams params = new FieldParams();
		Resultset data;
		Account acct;
		Resultset res;
		CodeList codelist;
		
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
		
		if (query.get(Account.STATUSID) != null) {
			res = new Resultset();
			codelist = getCodeListByValue(accountStatus, query.get(Account.STATUSID));
			params.put(Account.STATUSID, Long.toString(codelist.getId()));
			data = Server.database.select(BusinessClass.ACCOUNT, params);
			
			for (BusinessObject object : data) {
				params.clear();
				params.put(User.ID, Long.toString(((UserInfo)object).getUserId()));
				res.addAll(Server.database.select(BusinessClass.ACCOUNT, params));
			}
		} else {
			res = Server.database.select(BusinessClass.ACCOUNT, request.getQuery());
		}
		
		for (BusinessObject item : res) {
			params.clear();
			
			acct = (Account)item;
			params.put(Account.STATUSID, Long.toString(acct.getStatusId()));
			codelist = getCodeListById(accountStatus, acct.getStatusId());
			acct.setStatus(codelist.getValue());
		}
		
		return res;
	}
	
	/**
	 * Updates account status
	 * @param request - must define query and transaction
	 * @return number of records modified, should be 1 else 0
	 */
	public Resultset setAccountStatus(Request request) {
		FieldParams transact = new FieldParams();
		
		// Ensure we are only updating account status 
		transact.put(Account.STATUSID, Long.toString(getCodeListByValue(accountStatus, request.getTransaction().get(Account.STATUSID)).getId()));
		
		return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
	}

	///
	//	PRIVATE METHODS
	///
	
	private void init() {
		FieldParams params = new FieldParams();
		Resultset res;
		
		// init checkpoint/status data
		userCheckpoints = new ArrayList<>();
		params.put(CodeList.CODE, "USER-STATUS");
		
		// get data
		res = Server.database.select(BusinessClass.CODELIST, params);
		
		for (BusinessObject object : res) 
			userCheckpoints.add((CodeList)object);	

		// init status data
		accountStatus = new ArrayList<>();
		params.put(CodeList.CODE, "ACCOUNT-STATUS");
		
		// get data
		res = Server.database.select(BusinessClass.CODELIST, params);
				
		for (BusinessObject object : res) 
			accountStatus.add((CodeList)object);
		
		// init TYPE data
		accountType = new ArrayList<>();
		params.put(CodeList.CODE, "ACCOUNT-TYPE");
		
		// get data
		res = Server.database.select(BusinessClass.CODELIST, params);
				
		for (BusinessObject object : res) 
			accountType.add((CodeList)object);	
	}
	
	
	private CodeList getCodeListById(List<CodeList> list, long id) {
		CodeList code = null;
		
		for (CodeList item : list) {
			if (item.getId() == id) {
				code = item;
				break;
			}
		}
		
		return code;
	}
	
	private CodeList getCodeListByValue(List<CodeList> list, String value) {
		CodeList code = null;
		
		for (CodeList item : list) {
			if (item.getValue().equals(value.toUpperCase())) {
				code = item;
				break;
			}
		}
		
		return code;
	}
	
}
