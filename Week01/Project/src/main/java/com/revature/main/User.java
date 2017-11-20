package com.revature.main;

/**
 * Defines a User object as one with an ID, password, balance administrative
 * privileges flags and account standing flags.
 * 
 * Marked serializable so that User objects stored in an ArrayList can be
 * properly encrypted/decrypted.
 * 
 * Implements comparable so that natural sorting of list defaults to numerical
 * order by the account status flag.
 */
//public class User implements Serializable, Comparable<User> {
//	private static final long serialVersionUID = -530076356915821967L;
public class User implements Comparable<User> {
	
	private String ID;
	private String password;
	private double balance;
	private boolean isAdmin;
	private int statusFlag; // 0 - unapproved, 1 - approved, 2 - blocked, 3 - to delete)

	/**
	 * Empty constructor used for initialization and validation purposes
	 */
	public User() {
		this.ID = " ";
		this.password = " ";
		this.balance = 0;
		this.isAdmin = false;
		statusFlag = 0;
	}

	/**
	 * Constructor used to hardcode the first administrator.
	 * 
	 * @param ID
	 * @param password
	 * @param balance
	 * @param isAdmin
	 * @param Flag
	 */
	public User(String ID, String password, double balance, boolean isAdmin, int Flag) {
		this.ID = ID;
		this.password = password;
		this.balance = balance;
		this.isAdmin = isAdmin;
		statusFlag = Flag;
	}

	/**
	 * Getters
	 * 
	 * @return the current obj's username, password, balance, status flag, admin
	 *         flag
	 */
	// ––––––––Getters––––––––––––
	public String getUserID() {
		return this.ID;
	}

	public String getPswrd() {
		return password;
	}

	public double getBalance() {
		return balance;
	}

	public int getStatusFlag() {
		return statusFlag;
	}

	public boolean getAdmin() {
		return isAdmin;
	}

	/**
	 * Setters set the current obj's username, password, balance, status flag, admin
	 * flag
	 */
	// ––––––––Setters––––––––––––
	public void setUserID(String ID) {
		this.ID = ID;
	}

	public void setPswrd(String password) {
		this.password = password;
	}

	public void setBalance(double amount) {
		balance = amount;
	}

	public void setStatusFlag(int flag) {
		statusFlag = flag;
	}

	public void setAdmin(boolean decision) {
		isAdmin = decision;
	}

	/**
	 * @param id
	 *            used to find in List and
	 * @return corresponding User object else it returns an empty one
	 */
	public static User returnExisting(String id) {
		//replace w db access?
		return null;
	}

	/**
	 * Provides string output of User object
	 */
	@Override
	public String toString() {
		return "ID: " + getUserID() + "\tstatus: " + getStatusFlag() + "\tadmin: " + getAdmin();
	}

	/**
	 * defines natural sorting as by statusFlag
	 */
	@Override
	public int compareTo(User user) {
		if (this.getStatusFlag() < user.getStatusFlag())
			return -1;
		else if (this.getStatusFlag() > user.getStatusFlag())
			return 1;
		else
			return 0;
	}
}
