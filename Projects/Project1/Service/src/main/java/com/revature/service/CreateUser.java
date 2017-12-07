package com.revature.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.CodeList;

public class CreateUser {
	private static List<CodeList> roles = GetCodeList.getCodeListByCode("USER-ROLE");
	private static List<CodeList> stateCity = GetCodeList.getCodeListByCode("CITY-CODE-GROUP");
	
	public static Integer createUser(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CodeList code;
		String username;
		String firstname;
		String lastname;
		String email;
		String role;
		Integer result = 0 ;
		
		if (session != null && session.getAttribute("role").equals("BENEFIT-COORDINATOR")) {
			// TODO : be able to create users 
		}
		
		return result;
	}
}
