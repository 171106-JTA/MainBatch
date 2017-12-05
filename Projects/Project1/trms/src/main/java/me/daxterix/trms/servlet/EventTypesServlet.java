package me.daxterix.trms.servlet;

import me.daxterix.trms.config.Config;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.model.EventType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "eventTypes", urlPatterns = "/lookups/eventTypes")
public class EventTypesServlet extends StockHttpServlet {
    private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private ObjectDAO objectDao;
    {
         objectDao = context.getBean(ObjectDAO.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EventType> eventTypes = objectDao.getAllObjects(EventType.class);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (EventType t: eventTypes) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            objBuilder.add("type", t.getType());
            objBuilder.add("percentOff", t.getPercentOff());
            JsonObject tagsObj = objBuilder.build();
            arrBuilder.add(tagsObj);
        }
        JsonArray arr = arrBuilder.build();
        String arrJsonStr = arr.toString();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(arrJsonStr);
    }
}
