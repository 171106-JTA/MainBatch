package com.revature.users;

import java.io.Serializable;

public class User implements Serializable{
	//access_levels: 2 - admin; 1 - mod; 0 - user
	private int access_level;
	private double balance;
	private boolean approved;
	private String name;
	private String password;
	private boolean banned;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.approved = false;
		this.banned = false;
	}

	public int getAccess_level() {
		return access_level;
	}

	public void setAccess_level(int access_level) {
		this.access_level = access_level;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	@Override
	public String toString() {
		return "User [access_level=" + access_level + ", balance=" + balance + ", approved=" + approved + ", name="
				+ name + ", password=" + password + "]";
	}
	
}
