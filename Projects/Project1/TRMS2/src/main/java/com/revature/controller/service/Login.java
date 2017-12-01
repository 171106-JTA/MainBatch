package com.revature.controller.service;

public class Login {
	public boolean validateLogin(String user, String pass){
		if(user.toLowerCase().equals("bobbert") && pass.equals("b0bb3rt")){
			return true;
			
			/*
			 * UserDao = new UserDao
			 * User userObject = UserDao.getUserByName(user);
			 * 
			 * if(userObject==null)
			 * return false
			 * 
			 * pass.tolowerCase().equals(userObject.getPass)
			 * THEN return true
			 * 
			 */
			
		}
		return false;
	}
}
