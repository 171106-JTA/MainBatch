package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.Request;
import revature.trms.bean.User;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		//LoginCredentials lc = new LoginCredentials();
		DaoImpl dao = new DaoImpl();
		String username = request.getParameter("username");
		String password  = request.getParameter("password");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//out.println("<p>" + username + " " + password + "</p>");
		HttpSession session = null;
		if(dao.login_emp(username, password)){
			
			session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			session.setMaxInactiveInterval(3600);
			
			//response.sendRedirect("Profile");
			
			//response.sendRedirect("Empl.html");
			out.println("<h1>Welcome " + username + "</h1>");
			
			//response.sendRedirect("Profile");
			
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
			
		}
		else{
			out.println("<h1 style='color:red'>ACCESS DENIED</h1>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
	}

}
