package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class UpdateFormApproval
 */
public class UpdateFormApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside getFormsNeedingApproval");
		System.out.println("request.getParameter(\"reimbursementID\"): " + request.getParameter("reimbursementID"));
		System.out.println(request.getParameter("approvalByDirectSupervisor"));
		System.out.println(request.getParameter("approvalByDepartmentHead"));
		System.out.println(request.getParameter("approvalByBenCo"));
		
		int reimbursementID = Integer.parseInt(request.getParameter("reimbursementID"));
		int approvalByDirectSupervisor = Integer.parseInt(request.getParameter("approvalByDirectSupervisor"));
		int approvalByDepartmentHead = Integer.parseInt(request.getParameter("approvalByDepartmentHead"));
		int approvalByBenCo = Integer.parseInt(request.getParameter("approvalByBenCo"));
		
		TrmsDaoImplement dao = new TrmsDaoImplement();
		dao.updateFormApproval(
				reimbursementID, 
				approvalByDirectSupervisor, 
				approvalByDepartmentHead, 
				approvalByBenCo);
		System.out.println("Reimbursement Form Updated");
	}

}
