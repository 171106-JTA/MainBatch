package com.revature.bank;

public class DefaultAdmin extends Admin {
	
	static boolean alreadyCreated = false;
	private static DefaultAdmin DEFAULT_ADMIN;
	
	private DefaultAdmin() {
		this(null);
	}
	
	private DefaultAdmin(DataStore data) { 
		super("SuperUser", "ReVBaNk0x41646d696e", data);
		alreadyCreated = true;
	}
	
	public static DefaultAdmin getDefaultAdmin() {
		return getDefaultAdmin(null);
	}
	
	
	public static DefaultAdmin getDefaultAdmin(DataStore data) {
		if (!alreadyCreated) DEFAULT_ADMIN = new DefaultAdmin(data);
		return DEFAULT_ADMIN;
	}
}
