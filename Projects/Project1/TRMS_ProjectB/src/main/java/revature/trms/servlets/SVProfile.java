package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.Message;
import revature.trms.bean.Request;
import revature.trms.bean.User;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class SVProfile
 */
public class SVProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SVProfile() {
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
		HttpSession  session = request.getSession();
		String username = (String) session.getAttribute("username");
		DaoImpl dao = new DaoImpl();
		User user = dao.getUser(username);
		ArrayList<Message> messages = dao.getMessages();
		ArrayList<Request> reqs = dao.getRequests();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Double avail = dao.getAvail(username);
		Double taken = 1000 - avail;
		
		out.println("<h2>Welcome " + user.getFname() + "!</h2>");
		
		
		
		out.println("<h3>Awaiting Requests</h3>");
		out.println("<table border='3px'><tr>  <th>EMPLOYEE</th>  <th>REQUEST ID</th> <th>REQUESTED AMOUNT</th></tr>");
		for(Request req : reqs){
			if(req.getPending() == 1){
				out.println("<tr><th>" + req.getEmp_id() + "</th><th>" + req.getReq_id() + "</th><th>$" + req.getMoney() + "</th></tr>");
			}
			
		}//end for
		out.println("</table><hr>");
		
		out.println("<h3>Messages Received</h3>");
		out.println("<table border='3px'><tr><th>DATE</th><th>SENDER</th><th>MESSAGE</th></tr>");
		
		for(Message message : messages){
			if(message.getReceiver().equals(username))
				out.println("<tr><td>" + message.getDate() + "</td> <td>" + message.getSender() + "</td> <td>" + message.getMessage() + "</td></tr>");
		}
		
		out.println("</table><hr>");
		
		out.println("<h3>Messages Sent</h3>");
		out.println("<table border='3px'><tr><th>DATE</th><th>SENDER</th><th>MESSAGE</th></tr>");
		
		for(Message message : messages){
			if(message.getSender().equals(username))
				out.println("<tr><td>" + message.getDate() + "</td> <td>" + message.getSender() + "</td> <td>" + message.getMessage() + "</td></tr>");
		}
		
		out.println("</table>");
		
		out.println("<form action='SVSendMessage' method='post'><p>Receiver</p><input type='text' name='receiver'>"
				+ "<p>Message</p><input type='text' name='message'>"
				+ "<input type='submit' value='Send Message'></form>");
	}

}
