package me.daxterix.trms.servlet;

import me.daxterix.trms.model.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "DepartmentsServlet", urlPatterns="/lookups/departments")
public class DepartmentsServlet extends StockHttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> departments = objectDao.getAllObjects(Department.class).stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ServletUtils.stringsToJsonArrayString(departments));
    }
}
