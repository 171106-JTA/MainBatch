package com.miniproject1.bank;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5138046776588954161L;
	
	//simple login credentials with balance
	private String name;
	private String password;
	private double balance;
	
	//Used for loan
	private double debt;
	private boolean loanApplied;
	private double loanAmount;
	private String loanMessage;
	private boolean loanViewed;

	public boolean isLoanViewed() {
		return loanViewed;
	}

	public void setLoanViewed(boolean loanViewed) {
		this.loanViewed = loanViewed;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public boolean isLoanApplied() {
		return loanApplied;
	}

	public void setLoanApplied(boolean loanApplied) {
		this.loanApplied = loanApplied;
	}

	public String getLoanMessage() {
		return loanMessage;
	}

	public void setLoanMessage(String loanMessage) {
		this.loanMessage = loanMessage;
	}

	//flags used to determine the status of the account
	private boolean activated;
	private boolean locked;
	private boolean admin;
	
	/**
	 * The base constructor for a user that takes in the name and password for a user.
	 * When an user is created, the default values is set $0, false, false, and false for
	 * activated, locked, and admin respectively.
	 * @param name - username for the account
	 * @param password - password for the account
	 */
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		balance = 0;
		activated = false;
		locked = false;
		admin = false;
		
		debt = 0;
		loanApplied = false;
		loanViewed = false;
		loanAmount = 0;
		loanMessage = "No message";
		
	}
	
	//all the getters and setters for the user's data
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
	public double getBalance() {
		return balance;
	}
	
	public void printBalance() {
		System.out.println("Balance: $" + balance);
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	//Override hashCode() equals() to make it easier to compare
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result + (admin ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (activated != other.activated)
			return false;
		if (admin != other.admin)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (locked != other.locked)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", balance=" + balance + ", activated=" + activated
				+ ", locked=" + locked + ", admin=" + admin + "]\n";
	}
	
	/**
	 * Takes the user into withdrawing the amount in the parameter. If the amount exceeds the
	 * balance, the withdrawal will be denied.
	 * @param amount - amount to be withdrawn
	 */
	public void withdraw(double amount) {
		if(amount > balance) {
			System.out.println("Withdraw denied. Amount entered exceed current balance");
		}
		else {
			balance -= amount;
			System.out.println("Withdraw approved.");
		}
		//balance is always shown after each interaction
		printBalance();
	}
	
	/**
	 * Takes the user into depositing the amount in the parameter. 
	 * @param amount - Amount to be deposited
	 */
	public void deposit(double amount) {
		balance += amount;
		
		System.out.println("Deposit approved.");
		//INSERT CODE TO PRINT TO LOG HERE
		
		//balance is always shown after each interaction
		printBalance();
	}
	

}
