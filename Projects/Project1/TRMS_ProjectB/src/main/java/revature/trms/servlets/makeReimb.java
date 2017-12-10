package revature.trms.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import revature.trms.dao.DaoImpl;
import revature.trms.java.Function;

/**
 * Servlet implementation class makeReimb
 */
public class makeReimb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public makeReimb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String emp_id = request.getParameter("emp_id");
		DaoImpl dao = new DaoImpl();
		ArrayList<String> heads = dao.getHeads();
		String uc = request.getParameter("uc");
		String sem = request.getParameter("sem");
		String cpc = request.getParameter("cpc");
		String cert = request.getParameter("cert");
		String tt = request.getParameter("tt");
		String other = request.getParameter("other");
		
		String message1 = request.getParameter("message1");
		String message2 = request.getParameter("message2");
		String message3 = request.getParameter("message3");
		String message4 = request.getParameter("message4");
		
		String message = "REIMBURSEMENT REQUEST FOR " + emp_id + "<br>POSITION IN COMPANY:<br>" + message1
				+ "<br>CONTRIBUTIONS:<br>" + message2
				+ "<br>EDUCATION:<br>" + message3
				+ "<br>RESEARCH:<br>" + message4;
		
		Function func = new Function();
		double reimb = func.trms(Double.parseDouble(uc), Double.parseDouble(sem), Double.parseDouble(cpc), Double.parseDouble(cert), Double.parseDouble(tt), Double.parseDouble(other), emp_id);
		
		dao.make_reimbursement_request(emp_id, reimb);
		for(String head : heads){
			dao.sendMessage(emp_id, head, message);
		}
		response.sendRedirect("Profile.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
