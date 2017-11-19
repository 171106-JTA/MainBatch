package com.revature.model.account;

public class User {
	
	// private int uid;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private UserLevel userLevel;
	private String pin;
	private double balance;
	private boolean isLocked;
	private boolean isApproved;
	
	private static int genCount = 0;
	// Constructors
	public User(){
		this.userLevel = UserLevel.BASIC;
//		leave it to db
//
//		setGenCount(getGenCount() + 1);
//		uid = getGenCount(); 
	}
	
//	Getters and setter
//	
//	public int getUid() {
//		return uid;
//	}
//	public void setUid(int uid) {
//		this.uid = uid;
//	}
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
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
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
	public static int getGenCount() {
		return genCount;
	}
	public static void setGenCount(int genCount) {
		User.genCount = genCount;
	}
}
