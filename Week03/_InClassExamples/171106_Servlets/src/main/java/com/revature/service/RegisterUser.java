package com.revature.service;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Trainer;
import com.revature.dao.TrainerDao;

public class RegisterUser {
	public static boolean Register(HttpServletRequest request){
		TrainerDao td = new TrainerDao();
		Trainer t = new Trainer(
				request.getParameter("username"),
				request.getParameter("password"),
				request.getParameter("email"),
				request.getParameter("fname"),
				request.getParameter("mname"),
				request.getParameter("lname")
				);
		
		if(!(td.selectTrainerByUsername(request.getParameter("username"))==null)){
			System.out.println("test");
			return false;
		}
		td.insertTrainer(t);
		return true;
	}
}
