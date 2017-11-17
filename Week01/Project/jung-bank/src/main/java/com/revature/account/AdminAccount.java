package com.revature.account;

public class AdminAccount extends UserAccount {
	/**
	 * 
	 */
	private static final long serialVersionUID = 934864242145778783L;
	
	/**
	 * Instantiates a new admin account.
	 *
	 * @param username the username
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param pin the pin
	 * @param uid the uid
	 */
	public AdminAccount(String username, String password, String firstName, String lastName, String pin, int uid) {
		super(username, password, firstName, lastName, pin, uid);
	}
	
	/**
	 * Instantiates a new admin account.
	 *
	 * @param currentUser the current user
	 */
	public AdminAccount(UserAccount currentUser) {
		super();
	}

	/**
	 * Instantiates a new admin account.
	 */
	public AdminAccount() {
		super();
	}

	/**
	 * Promote user.
	 *
	 * @param client the client
	 * @return the admin account
	 */
	public AdminAccount promoteUser(UserAccount client) {
		// create new instance b/c downcasting is dangerous and
		// test will fail
		AdminAccount admin= new AdminAccount(client);
		admin.setUserLevel(UserLevel.ADMIN);
		return admin;
	}
	
	/**
	 * Lock user.
	 *
	 * @param user the user
	 * @return the user account
	 */
	public UserAccount lockUser(UserAccount user) {
		user.setLocked(true);
		return user;
	}
	
	/**
	 * Unlock user.
	 *
	 * @param user the user
	 * @return the user account
	 */
	public UserAccount unlockUser(UserAccount user) {
		user.setLocked(false);
		return user;
	}
}
