package com.revature.bank;

import java.util.LinkedList;

public class Users extends LinkedList<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	/**
	 * Gets user by name
	 * @param name : the name of the user to try to get
	 * @return the User that has that name, or null if there isn't one found
	 */
	public User getByName(String name)
	{
		for (User u : this)
		{
			if (u.getName().equals(name)) return u;
		}
		
		return null;
	}
	
	/**
	 * Gets index at which User can be found with a specified name
	 * @param name : the name of the User to look up by 
	 * @return the index of this where that User can be found, or -1 if this doesn't contain a User by that name
	 */
	public int getIndexByUserName(String name)
	{
		int j = 0;
		for (User u : this)
		{
			if (u.getName().equals(name)) return j;
			j++;
		}
		return -1;
	}
}
