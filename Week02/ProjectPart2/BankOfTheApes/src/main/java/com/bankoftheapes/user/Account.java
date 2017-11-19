package com.bankoftheapes.user;

public class Account {
	private int dollars;
	private int cents;
	private int AccId;
	public Account(int dollars, int cents, int accId) {
		super();
		this.dollars = dollars;
		this.cents = cents;
		AccId = accId;
	}
	public int getDollars() {
		return dollars;
	}
	public void setDollars(int dollars) {
		this.dollars = dollars;
	}
	public int getCents() {
		return cents;
	}
	public void setCents(int cents) {
		this.cents = cents;
	}
	public int getAccId() {
		return AccId;
	}
	public void setAccId(int accId) {
		AccId = accId;
	}
	
	@Override
	public String toString() {
		return "Account [dollars=" + dollars + ", cents=" + cents + ", AccId=" + AccId + "]";
	}
	
	
	
}
