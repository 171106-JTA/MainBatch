package com.banana.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.SystemDAOImpl;

public class InsertReimbursement {
	
	public static boolean request(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SystemDAOImpl dao = new SystemDAOImpl();
		HttpSession session = request.getSession();
		
		if(session.isNew()) {
			session.invalidate();
			return false;
		}
		
		String location = request.getParameter("location");
		String descript = request.getParameter("description");
		String cost = request.getParameter("cost");
		String grading = request.getParameter("grading");
		String event = request.getParameter("event");
		String justify = request.getParameter("justification");
		String username = (String)session.getAttribute("username");
		Integer empId = (Integer)session.getAttribute("empId");
		
		
		
		
		ReimburseRequest rr = new ReimburseRequest(location, descript, Double.parseDouble(cost), Integer.parseInt(grading), event, justify, empId);
		
		dao.submitRequest(rr);
		
		return true;
	
	}
}

