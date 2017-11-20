package com.revature.dao;

//useful functions to interact w DB
public interface UserDao {
	
	/* 
	 * inserts new user into DB
	 */
	public void createNewUser();
	
	/* 
	 * deletes user from DB
	 */
	public void deleteUser();
	
	/* 
	 * updates/sets user account status in DB
	 */
	public void setAccountStatus();

	/* 
	 * updates/sets user admin status in DB
	 */
	public void setAdminStatus();
	
	/* 
	 * stores transaction type, amt, initiating user, recipient user in DB
	 */
	public void storeTransaction();
}
