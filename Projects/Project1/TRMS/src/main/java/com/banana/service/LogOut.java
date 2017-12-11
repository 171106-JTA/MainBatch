package com.banana.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.log.Logging;

public class LogOut {

	/**
	 * Logs out the user by invalidating session
	 * 
	 * @param request contains the session object to invalidate
	 */
	public static void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(!session.isNew()) {
			System.out.println("Signed out");
			Logging.startLogging(session.getAttribute("username") + " has logged out");
			session.invalidate();
		}
	}
}
