package com.bankoftheapes.user;

import java.io.Serializable;

public class User implements Serializable{
	private String access_level;
	private int accId;
	private int loanId;
	private int approved; //field for approval status of account
	private String name;
	private String password;
	private int banned; //field for locking an account
	private Loan loan;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.approved = 0;
		this.banned = 0;
	}
	
	public User() {
		
	}
	
	public Loan getLoan() {
		return loan;
	}
	
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public String getAccess_level() {
		return access_level;
	}

	public void setAccess_level(String access_level) {
		this.access_level = access_level;
	}

	public int isBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}
	
	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int isApproved() {
		return approved;
	}

	public void setApproved(int approved) {
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
		return "User [access_level=" + access_level + ", approved=" + approved + ", name="
				+ name + ", password=" + password + "]";
	}
	
}
