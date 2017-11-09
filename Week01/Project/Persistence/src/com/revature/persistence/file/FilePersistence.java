package com.revature.persistence.file;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.factory.BusinessObjectFactory;
import com.revature.persistence.Persistenceable;

/**
 * 
 * @author Antony Lulciuc
 */
public class FilePersistence implements Persistenceable {
	// File Names
	public static final String USERFILE = "user.ser";
	public static final String USERINFOFILE = "user_info.ser";
	public static final String ACCOUNTFILE = "account.ser";
	
	// Self
	private static FilePersistence manager;
	
	// Logger
	private static Logger logger = Logger.getLogger(FilePersistence.class);
	
	// Where to save files generated by class
	private static String directory = System.getProperty("user.dir") + "\\data\\";  
	
	// Used to generate objects from FieldParam
	private static BusinessObjectFactory factory = BusinessObjectFactory.getFactory();
	
	// Used to read and write BusinessObjects from/to file
	private static BusinessObjectFileIO io = new BusinessObjectFileIO();
	
	// Datasets used to cache system records
	private static List<User> users = new LinkedList<>();
	private static List<UserInfo> userInfo = new LinkedList<>();
	private static List<Account> accounts = new LinkedList<>();
	
	/**
	 * Initializes system data if any found 
	 */
	private FilePersistence() {
	}
	
	/**
	 * @return Creates single instance of FilePersistence 
	 */
	public static FilePersistence getManager() {
		return manager == null ? manager = new FilePersistence() : manager;
	}
	
	@Override
	public Resultset select(String name, FieldParams cnds) {
		// Log request
		logger.debug("select:>" + name + " with " + cnds.toString());
		
		switch (name.toLowerCase()) {
			case "user":
				return findUser(cnds);
			case "account":
				return findAccount(cnds);
			case "userinfo":
				return findUserInfo(cnds);
			default:
				// invalid type
				logger.warn(name + " is unknown type");
				return null;
		}
	}

	@Override
	public int update(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String name, FieldParams cnds, FieldParams values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case "user":
			case "admin":
			case "customer":
				return addUser((User)businessObject);
			case "userinfo":
				return addUserInfo((UserInfo)businessObject);
			case "account":
				return addAccount((Account)businessObject);
			default:
				return 0;
		}
	}

	@Override
	public int insert(String name, FieldParams values) {
		switch (name.toLowerCase()) {
			case "user":
				return addUser((User)factory.getBusinessObject(name, values));
			case "userinfo":
				return addUserInfo((UserInfo)factory.getBusinessObject(name, values));
			case "account":
				return addAccount((Account)factory.getBusinessObject(name, values));
			default:
				return 0;
		}
	}

	@Override
	public int delete(BusinessObject businessObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String name, FieldParams cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return path where system files are stored
	 */
	public static String getDirectory() {
		return directory;
	}
	
	/**
	 * Designates where data is stored in system
	 * @param directory - path to folder to store application data
	 */
	public static void setDirectory(String directory) {
		FilePersistence.directory = directory;
		
		// Update Manager data
		load();
	}
	
	public static void load() {
		Object[] data;
		
		// clear data
		clean();
		
		// Load user data
		if ((data = loadData(directory + USERFILE)) != null)
			users.addAll(Arrays.asList((User[])data));
		
		// Load user info data 
		if ((data = loadData(directory + USERINFOFILE)) != null)
			userInfo.addAll(Arrays.asList((UserInfo[])data));
		
		// Load account data 
		if ((data = loadData(directory + ACCOUNTFILE)) != null)
			accounts.addAll(Arrays.asList((Account[])data));
	}
	
	public static void deleteData() {
		// clear data
		clean();
		
		logger.debug("deleting *.ser files");
		
		// delete user file 
		io.deleteFile(directory + USERFILE);
		
		// delete info file
		io.deleteFile(directory + USERINFOFILE);
		
		// delete account file
		io.deleteFile(directory + ACCOUNTFILE);
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private Resultset findUser(FieldParams cnds) {
		Resultset found = new Resultset();
		Boolean result;
		
		// if No conditions provided return all
		if (cnds == null)
			found.addAll(users);
		else {
			for (User user : users) {
				// Reset compare result flag
				result = true;
				
				if (cnds.containsKey("id")) {
					
				}
				
				if (cnds.containsKey("username")) {
					
				}
				
				if (cnds.containsKey("password")) {
					
				}
				
				if (cnds.containsKey("role")) {
					
				}
			}
		}
		
		return null;
	}
	
	private Resultset findUserInfo(FieldParams cnds) {
		return null;
	}
	
	private Resultset findAccount(FieldParams cnds) {
		return null;
	}
	
	private static int addUser(User user) {
		// If failed to generate user
		if (user == null)
			return -1;
		
		users.add(user);
		saveUserData();
		return 1;
	}
	
	private static int addUserInfo(UserInfo info) {
		// If failed to generate user
		if (info == null)
			return -1;
		
		userInfo.add(info);
		saveUserInfoData();
		return 1;
	}
	
	private static int addAccount(Account acct) {
		// If failed to generate user
		if (acct == null)
			return -1;
		
		accounts.add(acct);
		saveAccountData();
		return 1;
	}
	
	
	
	/**
	 * Saves user data to file
	 */
	private static void saveUserData() {
		// Open write stream 
		io.openWriteStream(directory + USERFILE);
		
		// Save if file opened 
		if (io.isWriteStreamOpen()) {
			for (BusinessObject item : users) {
				io.write(item);
			}
		}
		
		// free resources 
		io.close();
	}
	
	/**
	 * Saves user info data to file
	 */
	private static void saveUserInfoData() {
		// Open write stream 
		io.openWriteStream(directory + USERINFOFILE);
		
		// Save if file opened 
		if (io.isWriteStreamOpen()) {
			for (BusinessObject item : userInfo) {
				io.write(item);
			}
		}
		
		// free resources 
		io.close();
	}
	
	/**
	 * Saves account data to file
	 */
	private static void saveAccountData() {
		// Open write stream 
		io.openWriteStream(directory + ACCOUNTFILE);
		
		// Save if file opened 
		if (io.isWriteStreamOpen()) {
			for (BusinessObject item : accounts) {
				io.write(item);
			}
		}
		
		// free resources 
		io.close();
	}
	
	
	/**
	 * Loads Data into memory
	 * @param path where to read data
	 * @return Contents of file if successful else null
	 */
	private static Object[] loadData(String path) {
		List<Object> data = new LinkedList<>();
		BusinessObject object;
		
		// Open write stream 
		io.openReadStream(path);
				
		// Save if file opened 
		if (io.isReadStreamOpen()) {
			while ((object = io.read()) != null)
				data.add(object);
		}
				
		// free resources 
		io.close();
		
		return data.size() > 0 ? data.toArray() : null;
	}
	
	/**
	 * Clear collections 
	 */
	private static void clean() {
		users.clear();
	}
}
