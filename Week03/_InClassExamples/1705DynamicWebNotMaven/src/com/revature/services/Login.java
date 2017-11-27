package com.revature.services;

public class Login {
	public boolean validateLogin(String user, String pass){
		if(user.equals("bobbert") && pass.equals("bobbert")){
			return true;
		}else{
			return false;
		}
	}
}
