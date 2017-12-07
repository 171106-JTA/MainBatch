package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.model.MimeType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name="MimeTypesServlet", urlPatterns="/lookups/mimeTypes")
public class MimeTypesServlet extends HttpServlet {
    ObjectDAO objectDao = DAOUtils.getObjectDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> mimeTypes = objectDao.getAllObjects(MimeType.class).stream()
                .map(MimeType::getMimeType)
                .collect(Collectors.toList());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ServletUtils.stringsToJsonArrayString(mimeTypes));
    }
}
