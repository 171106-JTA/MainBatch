package com.caleb.bank;

public class Driver {
	public static void main(String[] args) {
		BankController bankController = new BankController();
		bankController.loadUserData();
		bankController.getAndVerifyCredentials();
		bankController.enterUserPortal();
		bankController.saveInformation();
	}
}
