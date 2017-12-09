package me.daxterix.trms.filter;

import javax.servlet.*;
import java.io.IOException;

import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.EmployeeRank;

public class BencoFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Employee emp = (Employee) req.getAttribute("employee");
        if (!emp.getRank().getRank().equals(EmployeeRank.BENCO)) {
            resp.setContentType("text/plain");
            resp.getWriter().print("Invalid credentials to view company wide requests");
        }

        else
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}
