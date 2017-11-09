package com.peterson.bowsercastle;

/**
 * User class that holds data for each user and methods to retrieve the data.
 * @author Alex Peterson
 */
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 8191660665486147873L;
	private String name;
	private int hashedPassword, coins;
	private Role role;
	
	/**
	 * Create a new user
	 * @param name of the new user
	 * @param hashedPassword of the user's password
	 * @param role of the user
	 * @param salary of the user
	 */
	public User(final String name, final int hashedPassword, final Role role) {
		this.name = name;
		this.hashedPassword = hashedPassword;
		this.role = role;
	}
	
	/**
	 * @return how many coins the user has
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Set the coins of the user
	 * @param coins new amount of coins
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the hashed password of the user
	 */
	public int getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @return the role of the user
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Set a new role for the user.
	 * @param role, new role for the user
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * A user's salary is determined by their role.
	 * @return the salary of the user
	 */
	public int getSalary() {
		return role.getSalary();
	}	
}