package com.revature.trms.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.ReimbursementCase;

public class ViewData {
	public static void getReimbursementCases(HttpServletResponse response) {
		EmployeeDAO empData = new EmployeeDAO();
		List<ReimbursementCase> reimCases = empData.getAllCases();
		response.setContentType("json/application");
		
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<ReimbursementCase>>() {}.getType();
			String json = gson.toJson(reimCases,type);
			out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
