package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class Logout extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		System.out.println("logging out user");
		session.invalidate();
		String json = String.format("{\"%s\" : \"%s\",\"%s\" : \"%s\"}", 
				"action", "logged_out",
				"redirect_url", "index.html");
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		res.setStatus(200);
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		// TODO : Implement this
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
}
