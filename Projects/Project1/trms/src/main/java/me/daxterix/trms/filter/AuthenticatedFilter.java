package me.daxterix.trms.filter;

import me.daxterix.trms.model.Employee;
import me.daxterix.trms.service.AuthenticationService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * all requests made to url pattern /employee/*, which require a login
 * are filtered here, and allowed to continue on only if user is logged in,
 * ie the request has a session.
 *
 */
@WebFilter(filterName="authenticatedFilter", urlPatterns="/employee/*")
public class AuthenticatedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Login in filter triggered");
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();

        HttpServletResponse httpResponse = (HttpServletResponse)response;
        if (session.isNew()) {
            session.invalidate();
            httpResponse.setStatus(401);
            httpResponse.getWriter().println("Please log in and try again.");
            return;
        }

        String email = (String)session.getAttribute("email");
        String pass = (String)session.getAttribute("password");
        Employee emp = AuthenticationService.checkEmployee(email, pass);
        if (emp == null) {
            session.invalidate();
            httpResponse.getWriter().println("Please log in and try again.");
        }
        else {
            request.setAttribute("employee", emp);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
