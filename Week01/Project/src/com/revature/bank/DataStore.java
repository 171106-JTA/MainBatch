package com.revature.bank;

import java.io.Serializable;
import java.util.LinkedList;

public class DataStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	private Users _activeUsers, _lockedUsers, _bannedUsers;
	private Admins _admins;
	private Accounts _accounts;
	/**
	 * The time at which this was created/initialized, in milliseconds. 
	 */
	public final long timeStamp;
	
	/**
	 * Instantiates a blank DataStore for the application to use. 
	 */
	public DataStore()
	{
		this(new Users(), new Users(), new Users(), new Admins(), new Accounts());
	}
	
	/**
	 * Instantiates a DataStore for this application to use, complete with the data passed to it
	 * @param active : list of active users
	 * @param locked : list of locked users
	 * @param banned : list of banned users
	 * @param admins : list of admins
	 * @param accounts : list of accounts
	 */
	public DataStore(Users active, Users locked, Users banned, Admins admins, Accounts accounts)
	{
		_activeUsers = active;
		_lockedUsers = locked;
		_bannedUsers = banned;
		_admins = admins;
		_accounts = accounts;
		timeStamp = System.currentTimeMillis();
		System.out.println("Time initialized : " + timeStamp);
	}

	/**
	 * 
	 * @return the locked users
	 */
	public Users getLockedUsers() {
		return _lockedUsers;
	}

	/**
	 * Updates the locked users for this application
	 * @param lockedUsers : the updated list of locked users
	 */
	public void setLockedUsers(Users lockedUsers) {
		_lockedUsers = lockedUsers;
	}

	/**
	 * 
	 * @return the banned users for this application
	 */
	public Users getBannedUsers() {
		return _bannedUsers;
	}

	/**
	 * Updates the list of banned users
	 * @param bannedUsers : the new list of banned users
	 */
	public void setBannedUsers(Users bannedUsers) {
		_bannedUsers = bannedUsers;
	}

	/**
	 * 
	 * @return the active accounts
	 */
	public Users getActiveUsers() {
		return _activeUsers;
	}

	/**
	 * Updates the list of active users
	 * @param activeUsers : the new list of active users
	 */
	public void setActiveUsers(Users activeUsers) {
		_activeUsers = activeUsers;
	}
	/**
	 * 
	 * @return the Admins written to this DataStore
	 */
	public Admins getAdmins() {
		return _admins;
	}
	
	/**
	 * Updates the list of admins
	 * @param newAdmins
	 */
	public void setAdmins(Admins newAdmins) { 
		_admins = newAdmins;
	}

	/**
	 * Returns the list of bank accounts on this application
	 * @return all the Accounts created on this application
	 */
	public Accounts getAccounts() {
		return _accounts;
	}

	/**
	 * Sets the list of bank accounts on this application. This method might not ever get used, but is only here 
	 * to complete this object as a POJO.
	 * @param accounts : the new list of bank accounts
	 */
	public void setAccounts(Accounts accounts) {
		_accounts = accounts;
	}
	
	
	
}
