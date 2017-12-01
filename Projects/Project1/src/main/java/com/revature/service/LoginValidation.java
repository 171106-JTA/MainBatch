package com.revature.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.bean.User;
import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class Login
 */
public class LoginValidation {
	private TrmsDaoImplement dao;

	/**
	 * Default constructor.
	 */
	public LoginValidation() {
		dao = new TrmsDaoImplement();
	}

	public void validateLoginCredentials(String username, String password)
			throws ServletException, IOException {
		
		System.out.println(username + ", " + password);
	}
}
