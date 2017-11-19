package com.revature.processor.handler;

import java.util.Arrays;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Status;
import com.revature.businessobject.info.account.Type;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.handler.helper.GenericHelper;
import com.revature.server.Server;
import com.revature.server.session.require.Require;

/**
 * Defines user account management process operations 
 * @author Antony Lulciuc
 */
public final class UserRequestHandler {
	
	/**
	 * Performs select query on database for user with specified username and password
	 * @param request request with username and password
	 * @return User BusinessObject if found else empty resultset 
	 */
	public Resultset login(Request request) throws RequestException {
		Resultset res = Server.database.select(BusinessClass.USER, request.getQuery());
		
		// If user found then validate checkpoint 
		if (res.size() > 0) {
			User user = (User) res.get(0);
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
		BusinessObject user = GenericHelper.getLargest(BusinessClass.USER, Arrays.asList(new String[] { User.ID }));
		FieldParams transact = request.getTransaction();
		FieldParams verify = new FieldParams();
		
		// Ensure data does not already exist in the database 
		verify.put(User.USERNAME, transact.get(User.USERNAME));
		Require.requireUnique(BusinessClass.USER, verify, request);
		
		// Update User id
		transact.put(User.ID, user != null ? Long.toString(((User)user).getId() + 1) : "0");
		
		// If non-admin then set account to pending 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) 
			transact.put(User.CHECKPOINT, Checkpoint.PENDING);
		
		// Insert user 
		return new Resultset(Server.database.insert(BusinessClass.USER, request.getTransaction()));
	}
	
	/**
	 * Deletes user and all data associated with them
	 * @param request - must define query for admin accounts 
	 * @return total number of records removed 
	 */
	public Resultset deleteUser(Request request) {
		FieldParams userdata = new FieldParams();
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
		userdata.put(Info.USERID, userid);
		userdata.put(User.ID, userid);
		
		// Remove all records associated with this id
		total = Server.database.delete(BusinessClass.USERINFO, userdata);
		total += Server.database.delete(BusinessClass.ACCOUNT, userdata);
		total += Server.database.delete(BusinessClass.USER, userdata);
		
		return new Resultset(total);
	}
	
	/**
	 * Performs query of User records 
	 * @param request conditions users must have to be in resultset
	 * @return 0 to n user records
	 */
	public Resultset getUser(Request request) throws RequestException {
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
		
		return Server.database.select(BusinessClass.USER, request.getQuery());
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
			
			// Users are not allowed to update checkpoints 
			trans.remove(User.CHECKPOINT);
		}
		
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
		FieldParams conditions = new FieldParams();
		
		// UserInfo record must not exist 
		Require.requireUnique(BusinessClass.USERINFO, request.getTransaction(), request);
		
		// Set condition params
		conditions.put(User.ID, Long.toString(request.getUserId()));
		
		// User account must exist before it can be created 
		Require.requireExists(BusinessClass.USER, conditions, request);
		
		return new Resultset(Server.database.insert(BusinessClass.USERINFO, request.getTransaction()));
	}
	
	/**
	 * Acquires UserInfo records
	 * @param request - query must be defined 
	 * @return 0 to n userinfo records
	 * @throws RequestException
	 */
	public Resultset getUserInfo(Request request) throws RequestException { 
		// For NON-ADMINS only personal account information should be accessible 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) 
			Require.requireSelfQuery(request);
		
		return Server.database.select(BusinessClass.USERINFO, request.getQuery());
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
		FieldParams transact = new FieldParams();
		BusinessObject largest;
		String number;
		
		// Get account number 
		largest = GenericHelper.getLargest(BusinessClass.ACCOUNT, Arrays.asList(new String[] { Account.NUMBER }));
		number = largest == null ? "0" : Long.toString(((Account)largest).getNumber() + 1);
		
		// Set account details
		transact.put(Info.USERID, Long.toString(request.getUserId()));
		transact.put(Account.TYPEID, type);
		transact.put(Account.STATUSID, Status.PENDING);
		transact.put(Account.NUMBER, number);

		switch (type) {
			case Type.CHECKING:
				transact.put(Checking.BALANCE, Float.toString(0.0f));
				break;
			case Type.CREDIT:
				transact.put(Credit.TOTAL, Float.toString(0.0f));
				transact.put(Credit.INTEREST, Float.toString(0.0f));
				transact.put(Credit.CREDITLIMIT, Float.toString(0.0f));
				break;
			default:
				throw new RequestException(request, "Unknown account type=[\'" + type + "\']!");	
		}
		
		
		return new Resultset(Server.database.insert(BusinessClass.ACCOUNT, transact));
	}
	
	/**
	 * Removes specified account records from system
	 * @param request - must have query defined 
	 * @return number of records deleted
	 * @throws RequestException
	 */
	public Resultset deleteAccount(Request request) throws RequestException {
		// If non-admin account, then we should be deleting own account ONLY
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN))
			Require.requireSelfQuery(request);
			
		return new Resultset(Server.database.delete(BusinessClass.ACCOUNT, request.getQuery()));
	}
	
	/**
	 * Performs query to database for user Accounts 
	 * @param request - query must be defined 
	 * @return 0 to n account records
	 * @throws RequestException
	 */
	public Resultset getAccount(Request request) throws RequestException {
		// For NON-ADMINS only personal account information should be accessible 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) 
			Require.requireSelfQuery(request);
				
		return Server.database.select(BusinessClass.ACCOUNT, request.getQuery());
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
		
		// For NON-ADMINS only personal account information should be accessible 
		if (!request.getCheckpoint().equals(Checkpoint.ADMIN)) 
			Require.requireSelfQuery(request);
				
		// Set type of account we are looking for 
		query.put(Account.TYPEID, type);
	
		return Server.database.select(BusinessClass.ACCOUNT, query);
	}
	
	/**
	 * Updates account status
	 * @param request - must define query and transaction
	 * @return number of records modified, should be 1 else 0
	 */
	public Resultset setAccountStatus(Request request) {
		FieldParams transact = new FieldParams();
		
		// Ensure we are only updating account status 
		transact.put(Account.STATUSID, request.getTransaction().get(Account.STATUSID));
		
		return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
	}
}
