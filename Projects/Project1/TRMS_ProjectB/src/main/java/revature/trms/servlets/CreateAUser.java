package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class CreateAUser
 */
public class CreateAUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAUser() {
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
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		
		DaoImpl dao = new DaoImpl();
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(dao.login_bc(emp_id, password) || dao.login_dh(emp_id, password) || dao.login_emp(emp_id, password)){
			out.println("<h1>This user account already exists!</h1>");
			request.getRequestDispatcher("CreateAUser.html").include(request, response);
		}else{
			dao.create_user(fname, lname, emp_id, password, email, address, Integer.parseInt(ssn), Integer.parseInt(phone));
			out.println("<h1>Your account has been set up! Go ahead and <a href='index.html'>login</a>!</h1>");
			out.println("<a href='index.html'>Go to login page.</a>");
			
		}
		
	}

}
