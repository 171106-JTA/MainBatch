package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		HttpSession session = request.getSession();
		String permission = (String) session.getAttribute("permission");
		
		if(permission.equals("direct_supervisor")) {
			approvalByDirectSupervisor = 1;
		} else if(permission.equals("department_head")) {
			approvalByDepartmentHead = 1;
		} else if(permission.equals("benco")) {
			approvalByBenCo = 1;
		}
		TrmsDaoImplement dao = new TrmsDaoImplement();
		dao.updateFormApproval(
				reimbursementID, 
				approvalByDirectSupervisor, 
				approvalByDepartmentHead, 
				approvalByBenCo);
	}

}
