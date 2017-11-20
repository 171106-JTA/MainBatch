package com.revature.model.account;

public class User {

	private int uid;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private UserLevel userLevel;
	private int pin;
	private double balance;
	private boolean isLocked;
	private boolean isApproved;

	// Constructors
	public User() {
		this.balance = 0.0d;
		this.userLevel = UserLevel.BASIC;
		this.isApproved = false;
	}

	

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", userLevel=" + userLevel + ", pin=" + pin + ", balance=" + balance
				+ ", isLocked=" + isLocked + ", isApproved=" + isApproved + "]";
	}



	// Getters and setter
	//
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

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

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
