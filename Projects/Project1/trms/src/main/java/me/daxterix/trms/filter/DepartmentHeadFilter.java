package me.daxterix.trms.filter;

import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.EmployeeRank;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(filterName="departmentFilter", urlPatterns="/employee/benco/requests")
public class DepartmentHeadFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Employee emp = (Employee) req.getAttribute("employee");

        String rank = emp.getRank().getRank();
        if (rank.equals(EmployeeRank.BENCO) || rank.equals(EmployeeRank.DEPARTMENT_HEAD))
            chain.doFilter(req, resp);
        else {
            resp.setContentType("text/plain");
            resp.getWriter().print("Invalid credentials to view company wide requests");
        }
    }

}
