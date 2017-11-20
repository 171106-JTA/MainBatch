package com.revature.bank;

import java.util.LinkedList;

public class Admins extends LinkedList<Admin> {
	
	private static final long serialVersionUID = 6L;
	/**
	 * Initializes this with a singleton `DefaultAdmin`, but without application data.
	 */
	public Admins()
	{
		super();
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
