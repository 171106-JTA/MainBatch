package me.daxterix.trms.servlet;

import me.daxterix.trms.model.Employee;
import me.daxterix.trms.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet(name="LoginServlet", urlPatterns="/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (session.isNew()) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Employee emp = AuthenticationService.checkEmployee(email, password);
            if (emp != null) {
                session.setAttribute("email", email);
                session.setAttribute("password", password);
                out.printf("LOGIN SUCCESS. Welcome %s\n", email);
            }
            else {
                session.invalidate();
                response.setStatus(401);
                out.printf("Invalid credentials\n");
            }
        }
        else {
            String email = ((String) session.getAttribute("email")).toUpperCase();
            out.println(String.format("ALREADY LOGGED IN AS: %s", email));
        }
    }
}
