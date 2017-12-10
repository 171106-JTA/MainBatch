package com.revature.trms.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.dao.ReimCaseDAO;
import com.revature.trms.model.Employee;
import com.revature.trms.model.ReimbursementCase;

public class ViewData {
	public static void getAllReimbursementCases(HttpServletResponse response) {
		ReimCaseDAO empData = new ReimCaseDAO();
		List<ReimbursementCase> reimCases = empData.getAllCases();
		response.setContentType("json/application");

		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<ReimbursementCase>>() {
			}.getType();
			String json = gson.toJson(reimCases, type);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getUserReimCases(HttpServletRequest request, HttpServletResponse response) {
		ReimCaseDAO caseData = new ReimCaseDAO();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		List<ReimbursementCase> reimCases = caseData.getAllCasesByUser(username);
		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<ReimbursementCase>>() {
			}.getType();
			String json = gson.toJson(reimCases, type);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getRCasesByStatus(HttpServletResponse response) {
		ReimCaseDAO rcd = new ReimCaseDAO();
		List<ReimbursementCase> rCase = rcd.getAllCasesByStatus(2);
		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<ReimbursementCase>>() {
			}.getType();
			String json = gson.toJson(rCase, type);
			System.out.println(json);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getRCasesBySup(HttpServletRequest request, HttpServletResponse response){
		ReimCaseDAO rcd = new ReimCaseDAO();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		EmployeeDAO ed = new EmployeeDAO();
		Employee emp = ed.selectEmployeeByUsername(username);
		
		List<ReimbursementCase> rCase = rcd.getAllCaseBySupId(emp.getSupervisorId());
		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<ReimbursementCase>>() {
			}.getType();
			String json = gson.toJson(rCase, type);
			System.out.println(json);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
