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
import com.revature.log.Logging;

public class InsertReimbursement {
	
	/**
	 * Inserts the reimbursement request into the database
	 * Calculates the actual reimbursement amount based on a table
	 * 
	 * @param request object contains needed parameters
	 * @param response object contains the message to client
	 * 
	 * @return boolean to determine success
	 * @throws ServletException
	 * @throws IOException
	 */
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
			System.out.println("here");
			Logging.startLogging(session.getAttribute("username") + " has submitted a request");
			UpdateEmployeeAmount.updateAvailAmount(empId, actualCost);
			return true;
		}
		
		Logging.startLogging(session.getAttribute("username") + " had an issue submitting a request");
		
		return false;
	
	}
	
	/**
	 * Calculates the actual reimbursement amount
	 * 
	 * @param cost inputed cost of event
	 * @param udao contains the conversion method
	 * @param event the event id needed to find the percentage
	 * @param currAmount the available amount of user
	 * 
	 * @return the result or -1 if the reimbursement exceeded the available amount
	 */
	private static double calcActualCost(double cost, UpdateDAOImpl udao, int event, double currAmount) {
		double percent = udao.getPercentage(event);
		
		double result = cost * percent;
		
		if(currAmount - result < 0) {
			return -1;
		}
		
		return result;
	}
	
	/**
	 * Converts a string to localdatetime
	 * 
	 * @param s string to convert to the LocalDateTime object
	 * 
	 * @return LocalDateTime, or null in case of exception
	 */
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

