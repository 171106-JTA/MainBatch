package com.banana.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOut {
	public static void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(!session.isNew()) {
			System.out.println("Signed out");
			session.invalidate();
		}
	}
}
