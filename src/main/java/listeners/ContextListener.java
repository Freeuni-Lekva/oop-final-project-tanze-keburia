package listeners;
import database.DatabaseConnector;
import database.FriendRequestDAO;
import database.FriendsDAO;
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
            dbc = DatabaseConnector.getInstance("jdbc:mysql://localhost:3306/metro",
                    "icosahedron", "Loko_kina1");
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
        FriendsDAO friendsDAO = null;
        FriendRequestDAO friendRequestDAO = null;
        try {
            userDAO = new UserDAO(conn);
            friendsDAO = new FriendsDAO(conn);
            friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("users", userDAO);
        servletContext.setAttribute("friends", friendsDAO);
        servletContext.setAttribute("friendRequests", friendRequestDAO);
    }
    public void contextDestroyed(ServletContextEvent event) {

    }
}
