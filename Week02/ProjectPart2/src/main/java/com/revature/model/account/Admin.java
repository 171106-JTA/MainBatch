package com.revature.model.account;

public class Admin extends User {
	public Admin(){
		this.setUserLevel(UserLevel.ADMIN);
	}

}
