package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class LoginFunc
 */
public class LoginFunc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginFunc() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		// TODO Auto-generated method stub
		String emp_id = request.getParameter("emp_id");
		String password = request.getParameter("password");
		
		PrintWriter out = response.getWriter();
		
		DaoImpl dao = new DaoImpl();
		//User user = dao.getUser(emp_id);
		
		if(dao.login_emp(emp_id, password)){
			HttpSession session = request.getSession();
			session.setAttribute("username", emp_id);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(3600);
			response.sendRedirect("Profile.html");
			
		}else if(dao.login_sv(emp_id, password)){
			HttpSession session = request.getSession();
			session.setAttribute("username", emp_id);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(3600);
			response.sendRedirect("SVProfile.html");

		}else if(dao.login_dh(emp_id, password)){
			HttpSession session = request.getSession();
			session.setAttribute("username", emp_id);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(3600);
			response.sendRedirect("DHProfile.html");
			
		}else if(dao.login_bc(emp_id, password)){
			HttpSession session = request.getSession();
			session.setAttribute("username", emp_id);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(3600);
			response.sendRedirect("BCProfile.html");
			
		}
		else{
			out.println("<h3>Access Denied!</h3>");
			request.getRequestDispatcher("Login.html").include(request, response);
		}
	}

}
