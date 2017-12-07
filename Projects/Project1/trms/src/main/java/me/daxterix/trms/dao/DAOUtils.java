package me.daxterix.trms.dao;

import me.daxterix.trms.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DAOUtils {
    static ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

    public static EmployeeDAO getEmployeeDAO() {
        return context.getBean(EmployeeDAO.class);
    }

    public static RequestDAO getRequestDAO() {
        return context.getBean(RequestDAO.class);
    }

    public static HistoryDAO getHistoryDAO() {
        return context.getBean(HistoryDAO.class);
    }

    public static RequestInfoDAO getRequestInfoDAO() {
        return context.getBean(RequestInfoDAO.class);
    }

    public static ObjectDAO getObjectDAO() {
        return context.getBean(ObjectDAO.class);
    }
}
