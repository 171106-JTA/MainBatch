package com.revature.bank;

import java.io.Serializable;
import java.util.List;

public class Admin extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	private DataStore _data;
	public static DefaultAdmin DEFAULT_ADMIN;
	
	public Admin(DataStore data)
	{
		this("", "", data);
	}
	/**
	 * Creates Admin by just their username and password
	 * @param name : the username of the Admin
	 * @param pass : the password of the Admin
	 * 
	 * WARNING: this Admin will *not* have access to any DataStore but the dummy DataStore. 
	 */
	public Admin(String name, String pass)
	{
		// 
		this(name, pass, new DataStore());
	}
	
	public Admin(String name, String pass, DataStore data)
	{
		super(name, pass, User.ACTIVE);
		_data = data;
		if (DEFAULT_ADMIN != null) DEFAULT_ADMIN = DefaultAdmin.getDefaultAdmin(data);
	}
	
	/** 
	 * gets the DataStore the Admin is working with
	 * NOTE: this function is only to be used by the Application!
	 * @return the DataStore 
	 */
	public DataStore getDataStore() { return _data; }
	
	/**
	 * provides data for Admin to work with
	 * @param data
	 */
	public void setDataStore(DataStore data)
	{
		_data = data;
	}
	
	/**
	 * activates a User, by username.  user's state will change to User.ACTIVE iff their state is "locked".
	 * @param username : the name of the user to activate
	 * @throws IllegalArgumentException if user not found
	 */
	public void activate(String username) throws IllegalArgumentException
	{
		// get the user 
		User user = findUserByName(username);
		if (user == null) throw new IllegalArgumentException("User not found");
		activate(user);
	}
	
	/**
	 * activates a User. user's state will change to User.ACTIVE iff their state is "locked". 
	 * @param user : the User to activate
	 */
	public void activate(User user)
	{
		/*// look for the user in data.getLockedUsers(), and if found
		Users lockedUsers = _data.getLockedUsers();
		int j = lockedUsers.getIndexByUserName(user.getName()); 
		if (j != -1)
		{
			// remove them from the list of locked users
			User nowActiveUser = lockedUsers.remove(j);
			// set their state to active
			nowActiveUser.setState(User.ACTIVE);
			// add them to the list of active users
			_data.getActiveUsers().add(nowActiveUser);
			
		}*/
		moveToList(_data.getActiveUsers(), _data.getLockedUsers(), user, User.ACTIVE);
	}
	
	/**
	 * locks a User, by username, iff they were active.
	 * @param username : the name of the user that is to be locked.
	 * @throws IllegalArgumentException if user not found
	 */
	public void lock(String username) throws IllegalArgumentException
	{
		User user = findUserByName(username);
		if (user == null) throw new IllegalArgumentException("User not found");
		lock(user);
	}
	
	/**
	 * locks a User, iff they were active.
	 * @param user : the User to lock
	 */
	public void lock(User user)
	{
		moveToList(_data.getLockedUsers(), _data.getActiveUsers(), user, User.LOCKED);
	}
	
	/**
	 * bans a User, by username.
	 * @param username : the name of the user to ban
	 * @throws IllegalArgumentException if user not found
	 */
	public void ban(String username) throws IllegalArgumentException
	{
		User user = findUserByName(username);
		if (user == null) throw new IllegalArgumentException("User not found");
		ban(user);
	}
	
	/**
	 * bans a User, iff they were either active or locked (trolls begone!)
	 * @param user : the User to ban
	 */
	public void ban(User user)
	{
		// get the list the user is currently in
		Users oldList = findInLists(user, _data.getActiveUsers(), _data.getLockedUsers());
		// if that list is either the list of active users or the list of locked users
		if ((oldList.equals(_data.getActiveUsers())) || (oldList.equals(_data.getLockedUsers())))
		{
			// move the user from that list to the list of banned users and change their state to banned
			moveToList(_data.getBannedUsers(), oldList, user, User.BANNED);
		}
	}
	
	/**
	 * promotes a User to Admin, by username.
	 * @param username : the name of the user to ban
	 * @throws IllegalArgumentException if user not found
	 */
	public void promote(String username) throws IllegalArgumentException
	{
		User user = findUserByName(username);
		if (user == null) throw new IllegalArgumentException("User not found");
		promote(user);
	}
	
	/**
	 * makes user an Admin
	 * @param user : the User to promote
	 */
	public void promote(User user)
	{
		// since user is not an Admin yet, we'll get ClassCastException if we try to make them one. We'll have to 
		//	just create a newAdmin the long way...
		Admin newAdmin = new Admin(user._name, user._pass, _data);
		moveToList(_data.getAdmins(), findInLists(user), newAdmin, user.getState());
	}
	
	/**
	 * Helper function that moves users from one list to another and assigns them a new state
	 * @param list : the list to move the user to 
	 * @param fromList : the list to move the user from
	 * @param user : the user
	 * @param newState : the new state for the user
	 * 
	 * WARNING: This function is now generic, which is dangerous since there is no template specialization in Java.
	 */
	private <T> void moveToList(List<T> list, Users fromList, User user, String newState)
	{
		// if user is already in list, we're done here.
//		if (list.getByName(user.getName()) != null) return;
		for (T elem : list)
		{
			if (((User)elem).getName().equals(user.getName())) return;
		}
		// find the user in fromList
		int j = fromList.getIndexByUserName(user.getName());
		// if found
		if (j != -1)
		{
			// remove them from that list
			user = fromList.remove(j);
			// change their state to newState
			user.setState(newState);
			// add them to list
			list.add((T) user);
		}
		// we just changed something in the data store. Update it for all the other Admins!
		writeToDataStore();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_data == null) ? 0 : _data.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		if (_data == null) {
			if (other._data != null)
				return false;
		} else if (!_data.equals(other._data))
			return false;
		/*if (_name == null) return other._name == null;
		if (_name.equals(other._name)) return true;*/
		return true;
	}
	/**
	 * Locates the list in which a User is found, directly from the data store this has access to
	 * @param user : the user to search for
	 * @return the list that User was found in
	 */
	private Users findInLists(User user)
	{
		return findInLists(user,
				_data.getActiveUsers(), 
				_data.getLockedUsers(),
				_data.getBannedUsers());
	}
	
	
	/**
	 * Locates the list in which a User is found
	 * @param user : the user to search for
	 * @param listsToSearch : the lists to search for the user
	 * @return the list that User was found in 
	 */
	private Users findInLists(User user, Users ... listsToSearch)
	{
		// for each list to search for 
		for (Users list : listsToSearch)
		{
			// if user is in that list
			if (list.getByName(user.getName()) != null)
				// return that list
				return list;
		}
		return null;
	}
	
	public User findUserByName(String username)
	{
		Users users = findInLists(new User(username, ""));
		if (users == null) return null;
		return users.getByName(username);
	}
	
	/**
	 * Determines if there are any base Users in the system
	 * @return whether or not there are any base Users in the system
	 */
	public boolean anyBaseUsers()
	{
		return anyActiveUsers() ||
				anyBannedUsers() ||
				!_data.getLockedUsers().isEmpty();
	}
	
	/**
	 * Determines if there are any active Users in the system
	 * @return whether or not there are any active users
	 */
	public boolean anyActiveUsers() { return !_data.getActiveUsers().isEmpty(); }
	/**
	 * Determines if there are any banned Users in the system
	 * @return whether or not there are any banned users
	 */
	
	public boolean anyBannedUsers() { return !_data.getBannedUsers().isEmpty(); }
	
	/**
	 * Determines if there are any locked Users in the system
	 * @return whether or not there are any locked users
	 */
	public boolean anyLockedUsers() { return !_data.getLockedUsers().isEmpty(); }
	
	private void writeToDataStore()
	{
		// update all the other Admins' DataStores with this one.
		for (Admin admin : _data.getAdmins())
		{
			if (!admin.equals(this))
			{
				admin.setDataStore(_data);
			}
		}
	}
	
	
}
