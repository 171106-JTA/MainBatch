package com.revature.bank;

import java.io.Serializable;
import java.util.List;

public class Admin extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	private int _adminID;

	public Admin()
	{
		
	}
	
	/**
	 * Creates Admin by just their username and password
	 * @param name : the username of the Admin
	 * @param pass : the password of the Admin
	 * 
	 */
	public Admin(String name, String pass)
	{
		super(name, pass, User.ACTIVE);
	}
	
	
	public Admin(int userID, String name, String pass)
	{
		super(name, pass);
		_userID = userID;
	}
	
	public Admin(int adminID, String user, String pass, String state)
	{
		super(user, pass, state);
		_adminID = adminID;
	}
	
	public Admin(int userID, int adminID, String name, String pass)
	{
		super(userID, name, pass, User.ACTIVE);
		_adminID = adminID;
	}
	
	public int getAdminID() { return _adminID; }
	public void setAdminID(int id) { _adminID = id; }
	
}
