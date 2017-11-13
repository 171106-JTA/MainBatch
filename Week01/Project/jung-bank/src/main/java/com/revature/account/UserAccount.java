package com.revature.account;

import java.io.Serializable;

public class UserAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5421834307940080818L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private UserLevel userLevel;
	private String pin;
	private int uid;
	private static int generationCount = 0;
	private double balance;
	private boolean isLocked;

	@Override
	public String toString() {
		return "UserAccount [username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", acctLevel=" + userLevel + ", pin=" + pin + ", uid=" + uid + ", balance="
				+ "$" + balance + ", " +"is it Locked? "+ isLocked + "]";
	}

	// Constructor
	public UserAccount() {
		super();
		this.userLevel = UserLevel.NORMIE;
		this.isLocked = true;
		generationCount++;
		uid = generationCount;
	}

	public UserAccount(String username, String password, String firstName, String lastName, String pin, int uid) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.uid = uid;
		this.balance = 0.0;
		this.userLevel = UserLevel.NORMIE;
		this.isLocked = true;
	}

	// Getters and setters below
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	public UserLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}
