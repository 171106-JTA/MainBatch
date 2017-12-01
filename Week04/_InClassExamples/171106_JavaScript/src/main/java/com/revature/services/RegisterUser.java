package com.revature.services;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Trainer;
import com.revature.dao.TrainerDao;

public class RegisterUser {
	public static boolean register(HttpServletRequest request){
		Trainer t = new Trainer(
					request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("email"),
					request.getParameter("fname"),
					request.getParameter("mname"),
					request.getParameter("lname")
				);
		TrainerDao td = new TrainerDao();
		if(td.selectTrainerByUsername(t.getUsername())!=null){
			return false;
		}
		td.insertNewTrainer(t);
		
		
		return true;
	}
}
