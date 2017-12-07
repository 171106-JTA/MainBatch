package com.banana.service;

import java.io.IOException;
import java.time.LocalDateTime;

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
		ReimburseRequest rr = null;
		
		if(session.isNew()) {
			session.invalidate();
			return false;
		}
		Integer empId = (Integer)session.getAttribute("empId");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String location = request.getParameter("location");
		String descript = request.getParameter("description");
		double cost = Double.parseDouble(request.getParameter("cost"));
		
		System.out.println(request.getParameter("grading") + " mid " + request.getParameter("event"));
		
		int grading = Integer.parseInt(request.getParameter("grading"));
		int event = Integer.parseInt(request.getParameter("event"));
		
		double actualCost = calcActualCost(cost, dao, event);
		
		String justify = request.getParameter("justification");
		LocalDateTime ldt = StringToLocalDateTime.convert((String)request.getParameter("datetime"));
		
		if(ldt == null) {
			return false;
		}
		
		
		rr = new ReimburseRequest(empId, fname, lname, location, descript, actualCost, grading, event, justify, ldt);
		
		if(dao.submitRequest(rr)) {
			return true;
		}
		
		return false;
	
	}
	
	private static double calcActualCost(double cost, SystemDAOImpl dao, int event) {
		double percent = dao.getPercentage(event);
		
		double result = cost * percent;
		
		return result;
	}
}

