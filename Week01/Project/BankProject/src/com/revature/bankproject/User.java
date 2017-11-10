package com.revature.bankproject;

public class User implements java.io.Serializable {	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7842889657628321177L;
	private String name;
	private String password;
	private String role;
	private boolean verify;

	private Account account = new Account();
	private double balance = account.balance;

	public User(String name, String password, String role, Account account, boolean verify) {
		super();
		this.name = name;
		this.password = password;
		this.role = role;
		this.account = account;
		this.verify = verify;
	}
	
	/*	private double balance;

	 * public User(String name, String password, String role, double balance, boolean verify) {
		super();
		this.name = name;
		this.password = password;
		this.role = role;
		this.balance = balance;
		this.verify = verify;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}*/

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", role=" + role + ", balance=" + account.balance + ", verify="
				+ verify + "]";
	}

	
	

}
