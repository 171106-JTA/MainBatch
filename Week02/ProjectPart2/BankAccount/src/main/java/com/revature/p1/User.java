package main.java.com.revature.p1;

import java.io.Serializable;

/**
 * @author Grantley Morrison
 * 
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	
	private String		name;
	private double		bal	= 0.0;
	private String		user;				// change to login to avoid confusion
	private String		pass;
	private boolean	approved;
	private boolean	admin	= false;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name
	 *           - the name of new user
	 * @param user
	 *           - the user of new user
	 * @param pass
	 *           - the pass of new user
	 */
	public User( String name , String user , String pass ) {
		this.name = name;
		bal = 0.0;
		this.user = user;
		this.pass = pass;
		approved = false;
	}
	
	public User( String name , String user , String pass , Double bal, Boolean approved, Boolean admin) {
		this.name = name;
		this.bal = bal;
		this.user = user;
		this.pass = pass;
		this.approved = approved;
		this.admin = admin;
	}
	
	/**
	 * Deposits money of x amount.
	 *
	 * @param x
	 *           - the value
	 * @return true, if successful
	 */
	public boolean deposit(double x) {
		if (approved) {
			bal += x;
			return true;
		} else {
			System.err.println("Error: '" + name + "' is not approved.");
			return false;
		}
		
	}
	
	/**
	 * Gets the balance (rounds to nearest tenth).
	 *
	 * @return the bal
	 */
	public double getBal() {
		bal = Math.round(bal * 100.0) / 100.0;
		return bal;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the pass.
	 *
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Checks if is admin.
	 *
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	/**
	 * Checks if is approved.
	 *
	 * @return approved status
	 */
	public boolean isApproved() {
		return approved;
	}
	
	/**
	 * Sets the admin status.
	 *
	 * @param admin
	 *           - boolean representing admin status
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	/**
	 * Sets the approved status.
	 *
	 * @param approved
	 *           - boolean representing approved status
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	/**
	 * Sets the pass.
	 *
	 * @param pass
	 *           - the new pass
	 */
	public void setPass(String pass) {
		if (approved) {
			this.pass = pass;
		} else {
			System.err.println("Error: User is not approved.");
		}
	}
	
	/**
	 * Sets the user.
	 *
	 * @param user
	 *           - the new username
	 */
	public void setUser(String user) {
		if (approved) {
			this.user = user;
		} else {
			System.err.println("Error: User is not approved.");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [name=" + name + ", bal=$" + bal + ", user=" + user + ", approved="
				+ approved + "]";
	}
	
	/**
	 * Withdraw.
	 *
	 * @param x
	 *           - the amount to withdraw
	 * @return true, if successful
	 */
	public boolean withdraw(double x) {
		if (approved) {
			double testBal = bal - x;
			if (testBal < 0) {
				System.err.println("Error: '" + name + "' has insufficient funds.");
				System.err.println("The approval status of '" + name
						+ "' has been revoked due to insufficient funds.");
				approved = false;
				return false;
			}
			bal = Math.round(testBal * 100.0) / 100.0;
			return true;
		} else {
			System.err.println("Error: '" + name + "' is not approved.");
			return false;
		}
		
	}
}
