package listeners;
import database.DatabaseConnectionPull;
import database.DatabaseConnector;
import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
//import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        DatabaseConnector dbc = null;
        try {
            String url = DatabaseConnectionPull.getUrl();
            String userName = DatabaseConnectionPull.getUserName();
            String password = DatabaseConnectionPull.getPassword();
            dbc = DatabaseConnector.getInstance(url, userName, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        try {
            conn = dbc.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("users", userDAO);
    }
    public void contextDestroyed(ServletContextEvent event) {

    }
}
