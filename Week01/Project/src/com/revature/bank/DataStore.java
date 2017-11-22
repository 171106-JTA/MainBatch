package com.revature.bank;

import java.io.Serializable;
import java.util.LinkedList;

public class DataStore implements Serializable {
	// 	TODO: make this singleton
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
	
	private static boolean hasBeenCreated = false;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_accounts == null) ? 0 : _accounts.hashCode());
		result = prime * result + ((_activeUsers == null) ? 0 : _activeUsers.hashCode());
		result = prime * result + ((_admins == null) ? 0 : _admins.hashCode());
		result = prime * result + ((_bannedUsers == null) ? 0 : _bannedUsers.hashCode());
		result = prime * result + ((_lockedUsers == null) ? 0 : _lockedUsers.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataStore other = (DataStore) obj;
		if (_accounts == null) {
			if (other._accounts != null)
				return false;
		} else if (!_accounts.equals(other._accounts))
			return false;
		if (_activeUsers == null) {
			if (other._activeUsers != null)
				return false;
		} else if (!_activeUsers.equals(other._activeUsers))
			return false;
		if (_admins == null) {
			if (other._admins != null)
				return false;
		} else if (!_admins.equals(other._admins))
			return false;
		if (_bannedUsers == null) {
			if (other._bannedUsers != null)
				return false;
		} else if (!_bannedUsers.equals(other._bannedUsers))
			return false;
		if (_lockedUsers == null) {
			if (other._lockedUsers != null)
				return false;
		} else if (!_lockedUsers.equals(other._lockedUsers))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}
	
	
	
}
