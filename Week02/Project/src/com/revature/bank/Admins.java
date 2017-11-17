package com.revature.bank;

import java.util.LinkedList;

public class Admins extends LinkedList<Admin> {
	
	private static final long serialVersionUID = 6L;
	/**
	 * Initializes this with a singleton `DefaultAdmin`, but without application data.
	 */
	public Admins()
	{
		// using this(new DataStore()) would cause a stack overflow. So, we simply pass null, and let the other
		//	constructor handle that.
//		this(new DataStore(new Users(), new Users(), new Users(), this, new Accounts()));
		this(null);
	}
	/**
	 * Initializes this with the application data, and a singleton `DefaultAdmin` to pass the data to.
	 * @param data: the application data to pass to the `DefaultAdmin`.
	 */
	public Admins(DataStore data)
	{
		super();
		// if we got null data
		if (data == null)
		{
			// construct a default DataStore containing this
			data = new DataStore(new Users(), new Users(), new Users(), this, new Accounts());
		}
		push(DefaultAdmin.getDefaultAdmin(data));
	}
	/**
	 * gets Admin by name
	 * @param name : the name of the Admin to try to fetch 
	 * @return the Admin, if found, or null if not found
	 */
	public Admin getAdminByName(String name)
	{
		for (Admin admin : this)
		{
			if (admin.getName().equals(name)) return admin;
		}
		return null;
	}
}
