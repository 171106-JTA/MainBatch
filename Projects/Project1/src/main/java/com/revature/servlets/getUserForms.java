package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.revature.bean.ReimbursementForm;
import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class getUserForms
 */
public class getUserForms extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = null;
		List<ReimbursementForm> RFs = null;
		
		//Check if user is logged in. If not, dispaly message that redirects to index.html
		if (session.isNew()) {
			response.setContentType("text/html");
			out = response.getWriter();
			out.println("You are not logged in!");
			out.println("<hr>" + "<a href='index.html'>BACK</a>");
			session.invalidate(); // Clears the session entirely
		} else {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			
			//To-Do: Write code for handling a NULL username
			String userName = (String) session.getAttribute("username");
			TrmsDaoImplement dao = new TrmsDaoImplement();
			RFs = dao.getUserForms(userName);
			
			JSONArray RF_JSON_Array = new JSONArray(RFs);
			
			//Return the JSON object of reimbursement forms
			out.println(RF_JSON_Array);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
