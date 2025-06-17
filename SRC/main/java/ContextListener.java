package main.java;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        Connection conn = DAOConnecter.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("users", userDAO);
    }
    public void contextDestroyed(ServletContextEvent event) {

    }
}
