package com.revature.trms.service;

public class Login {
	public boolean validateLogin(String user, String pw) {
		if(user.equals("bobbert") && pw.equals("bobbert")) {
			return true;
		}
		return false;
	}

}
