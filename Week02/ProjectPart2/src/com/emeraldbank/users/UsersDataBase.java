package com.emeraldbank.users;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.emeraldbank.controller.Consts;


/**
 * UsersDataBase class, containing the mapped user data for all users created in this application.
 * Contains methods to generate, search for, and manipulate users on its own and via the User class.
 * Also serializes user data and saves it to a file during user manipulation actions
 * @author sjgillet
 *
 */
public class UsersDataBase implements Serializable{

	private static final long serialVersionUID = 31266433370509169L;	
	private static ObjectOutputStream oos; 
	private static ObjectInputStream ois; 

	static Logger logger = Logger.getLogger(UsersDataBase.class); 

	private static UsersDataBase udb;
	private static HashMap<String, User> usersMap;

	/**
	 * Initializes the UsersDataBase class via instantiating references to itself,
	 * and initializes its user data map. Also attempts to read in 
	 * user data from file
	 */
	public static void initialize() {
		if(udb == null)
			udb = new UsersDataBase(); 
		if(usersMap == null)
			usersMap = new HashMap<String,User>(); 

		try {
			ois = new ObjectInputStream(new FileInputStream(Consts.USERS_SER_PATH)); 
			usersMap = (HashMap<String, User>) ois.readObject();
		} catch (FileNotFoundException | ClassNotFoundException nf) {
			logger.warn("No users save file found. Will be generated on creating adminPrime user");
		} catch (IOException ioe) {
			logger.warn("IOException caught in UsersDataBase.initialize(), at opening OIS"); 
		}		

		User adminPrime = new User("John", "Doe", "admin", "password");
		udb.addUser(adminPrime);
		udb.activateUser("admin"); 
		udb.promoteUser("admin"); 

	}

	/**
	 * Gets a reference to the udb's hashmap collection of users
	 * @return - the reference to this class' collection of users
	 */
	public static UsersDataBase getUsersDataBase() {
		initialize();
		return udb; 
	}

	/**
	 * Checks if the given userID is located in the usersMap collection
	 * @param userID - the user's ID to check for
	 * @return - true if the userID was found. False otherwise
	 */
	public boolean userExists(String userID) {
		return usersMap.containsKey(userID); 			
	}

	/**
	 * adds a given User object into the usersMap user collection. 
	 * @param u - the User object to add into the collection
	 */
	public void addUser(User u) {
		usersMap.put(u.getUserID(), u); 
		serialize(); 
	}

	/**
	 * Activates a given user's account for use in this application 
	 * @param userID - the login ID of the user to search for 
	 * @return - 1 if the user account was successfully activated. 
	 * 0 if the user was not found.
	 */
	public int activateUser(String userID) {
		if(userExists(userID)) {
			getUser(userID).setStatus(Consts.ACTIVE);
			serialize(); 
			return 1; 
		}
		else return 0; 
	}

	/**
	 * Removes the given user from the users collection via their userID
	 * @param userID - the login ID of the user to search for and remove
	 * @return - 1 if the user was successfully removed from the collection.
	 * 0 if the user was not found
	 */
	public int removeUser(String userID) {
		if(userExists(userID)) {
			usersMap.remove(userID);
			serialize(); 
			return 1; //matched user, removed successfully
		}
		else return 0; //did not match user	
	}

	/**
	 * Gets the User object with the given user ID
	 * @param userID - login ID for the user to search for
	 * @return - the User object found. null if it was not found
	 */
	public User getUser(String userID) {
		if(userExists(userID)) 
			return usersMap.get(userID); 
		else return null; 
	}
	
	/**
	 * Passes a bank account number to a user with the given login ID 
	 * to add to its portfolio of bank accounts
	 * @param acctNum - the account number of the account to add to the user
	 * @param userID - the login ID of the user to add the account to.
	 */
	public void addAccountToUser(String acctNum, String userID) {
		if(userExists(userID))
			usersMap.get(userID).addAcct(acctNum); 
	}

	/**
	 * Checks for a userID/password match. Checks the given userID's 
	 * login password. 
	 * @param userID - the login ID for the user to check on
	 * @param pw - the password to check agains the found user's password
	 * @return - 1 if the password matches the user whose login ID is userID. 
	 * 0 if the user was not found. -1 if the user was found but 
	 * the login password does not match.
	 */
	public int checkUserPassword(String userID, String pw) {
		if(userExists(userID)) {
			if(this.getUser(userID).getPassword().equals(pw))
				return 1; //matched password
			else
				return -1; //matched user, did not match password
		}
		else return 0; //did not match user		 			
	}

	/**
	 * Checks if the user is an administrator role
	 * @param userID - the login ID for the user to search for
	 * @return - 1 if the user is an administrator. -1 if not.
	 * 0 if the user was not found. 
	 */
	public int checkUserAdmin(String userID) {
		if(userExists(userID)) {
			if(this.getUser(userID).isAdmin())
				return 1;
			else
				return -1;
		}
		else return 0; 	
	}

	/**
	 * Checks if the user's account is active for use
	 * @param userID - the login ID for the user to search for
	 * @return - 1 if the user's account is active and usable.
	 * -1 if the user is not active and not usable.
	 * 0 if the user was not found. 
	 */
	public int checkUserActive(String userID) {
		if(userExists(userID)) {
			if(this.getUser(userID).isActive())
				return 1; //user is active
			else
				return -1; //user is not active
		}
		else return 0; //did not match user		
	}

	/**
	 * Get the user's activity status for usability
	 * @param userID - the login ID for the user to search for
	 * @return - 1 if the user's status is active and usable.
	 * 0 if the user's status is unactivated and not usable.
	 * -1 if the user's status is locked and not usable.
	 * -2 if the user's account was not found
	 */
	public int getStatus(String userID) {
		if(userExists(userID)) {
			return getUser(userID).getStatus();
		}
		else return -2; 
	}
	
	public String getUserStatusString(String userID) {
		if(userExists(userID)) {
			return getUser(userID).getStatusString();
		}
		else return "NOT FOUND"; 
	}

	/**
	 * Locks the user's account and bars them from activity 
	 * @param userID - the login ID for the user to lock
	 * @return - 1 if the user was locked successfully. 
	 * 0 if the user was not found.
	 * -1 if the user was not yet actiated before locking.
	 * -2 if the user was already locked. 
	 */
	public int lockUser(String userID) {
		if(userExists(userID)) {
			int s = getUser(userID).getStatus();
			getUser(userID).setStatus(Consts.LOCKED);
			if (s < 1) //if user was not active
				return s - 1; //user was locked (-2) or inactive (-1)
			else return 1; 
		}
		else return 0; //could not locate user by this userID
	}

	/**
	 * Promotes a given user to the administrator role.
	 * @param userID - the login ID of the user to promote
	 * @return - 1 if the user was promoted successfully.
	 * 0 if the user was not found.
	 * -1 if the user was already an administrator. 
	 */
	public int promoteUser(String userID) {
		if(userExists(userID)) {
			User u = usersMap.get(userID);
			if(!u.isAdmin()) {
				u.setAdmin(true);
				return 1; //successfully promoted to admin
			}
			else return -1; //user was already an admin
		}
		else return 0; //could not locate user by this userID
	}

	/**
	 * Serializes the usersMap collection and saves it to a file via the 
	 * ObjectOutputStream. 
	 * @return 
	 */
	public void serialize() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(Consts.USERS_SER_PATH));
			oos.writeObject(usersMap);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if(oos != null)
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}





}
