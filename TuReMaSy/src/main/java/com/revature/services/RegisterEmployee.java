package com.revature.services;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Application;
import com.revature.beans.Employee;
import com.revature.dao.MyDao;

public class RegisterEmployee {
	public static boolean register(HttpServletRequest request){
		Application t = new Application(
					request.getIntHeader("empID"),
					request.getParameter("empFirst"),
					request.getParameter("empLast"),
					request.getParameter("email")
				);
		MyDao td = new MyDao();
		if(td.selectApplicationByAppID(t.getAppID())!=null){
			return false;
		}
		td.insertNewApplication(t);
		
		
		return true;
	}
}