package me.daxterix.trms.servlet;

import me.daxterix.trms.config.Config;
import me.daxterix.trms.dao.ObjectDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;

public abstract class StockHttpServlet extends HttpServlet {
    protected ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    protected ObjectDAO objectDao;
    {
         objectDao = context.getBean(ObjectDAO.class);
    }
}

