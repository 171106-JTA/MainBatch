package com.revature.bank;

public class DefaultAdmin extends Admin {
	
	static boolean alreadyCreated = false;
	private static DefaultAdmin DEFAULT_ADMIN;
	
	private DefaultAdmin() {
		super("DefaultAdmin", "SuperUser");
		alreadyCreated = true;
	}
		
	public static DefaultAdmin getDefaultAdmin() {
		if (!alreadyCreated) DEFAULT_ADMIN = new DefaultAdmin();
		return DEFAULT_ADMIN;
	}	
}
