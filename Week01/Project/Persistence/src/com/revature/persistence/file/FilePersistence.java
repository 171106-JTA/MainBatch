package com.revature.persistence.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.comparator.FieldParamsUserComparator;
import com.revature.core.factory.BusinessObjectFactory;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.file.io.BusinessObjectFileIO;

/**
 * 
 * @author Antony Lulciuc
 */
public abstract class FilePersistence implements Persistenceable {
	// File Names
	public static final String USERFILE = "user.ser";
	public static final String USERINFOFILE = "user_info.ser";
	public static final String ACCOUNTFILE = "account.ser";
	
	// Logger
	protected static Logger logger = Logger.getLogger(FilePersistence.class);
	
	// Where to save files generated by class
	protected static String directory = System.getProperty("user.dir") + "\\data\\";  
	
	// Used to generate BusinessObjects from FieldParam
	protected static BusinessObjectFactory businessObjectFactory = BusinessObjectFactory.getFactory();
	
	// Used to generate FieldParams from BusinessObjects
	protected static FieldParamsFactory fieldParamsFactory = FieldParamsFactory.getFactory();
	
	// Used to read and write BusinessObjects from/to file
	protected static BusinessObjectFileIO io = new BusinessObjectFileIO();
	
	// Datasets used to cache system records
	protected static List<User> users = new LinkedList<>();
	protected static List<UserInfo> userInfo = new LinkedList<>();
	protected static List<Account> accounts = new LinkedList<>();

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
	// HELPER METHODS
	///
	
	/**
	 * Saves user data to file
	 */
	protected static void saveUserData() {
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
	protected static void saveUserInfoData() {
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
	protected static void saveAccountData() {
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
	protected static Object[] loadData(String path) {
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
	protected static void clean() {
		users.clear();
		userInfo.clear();
		accounts.clear();
	}
}
