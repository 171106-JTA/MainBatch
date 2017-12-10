package com.banana.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.UpdateDAOImpl;

public class InsertReimbursement {
	
	public static boolean request(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		HttpSession session = request.getSession();
		ReimburseRequest rr = null;
		double actualCost = 0;
		
		if(session.isNew()) {
			session.invalidate();
			return false;
		}
		
		double currAmount = (Double)session.getAttribute("currAmount");
		Integer empId = (Integer)session.getAttribute("empId");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String location = request.getParameter("location");
		String descript = request.getParameter("description");
		double cost = Double.parseDouble(request.getParameter("cost"));
		
		System.out.println(request.getParameter("grading") + " mid " + request.getParameter("event"));
		
		int grading = Integer.parseInt(request.getParameter("grading"));
		int event = Integer.parseInt(request.getParameter("event"));
		
		
		actualCost = calcActualCost(cost, udao, event, currAmount);
		
		String justify = request.getParameter("justification");
		LocalDateTime ldt = InsertReimbursement.convert((String)request.getParameter("datetime"));
		
		if(ldt == null || actualCost == -1) {
			return false;
		}
		
		
		rr = new ReimburseRequest(empId, fname, lname, location, descript, actualCost, grading, event, justify, ldt);
		
		if(udao.submitRequest(rr)) {
			UpdateEmployeeAmount.updateAmount(empId, cost);
			return true;
		}
		
		return false;
	
	}
	
	private static double calcActualCost(double cost, UpdateDAOImpl udao, int event, double currAmount) {
		double percent = udao.getPercentage(event);
		
		double result = cost * percent;
		
		if(currAmount - result < 0) {
			return -1;
		}
		
		return result;
	}
	
	public static LocalDateTime convert(String s) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(("MM-dd-yyyy HH:mm"));
		LocalDateTime ldt = null;
		try {
			ldt = LocalDateTime.parse(s, format);
		}catch(DateTimeParseException e) {
			return null;
		}
		
		
		return ldt;
	}
}
