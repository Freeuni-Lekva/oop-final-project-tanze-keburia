package listeners;

import database.*;


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

        dbc = DatabaseConnector.getInstance();


        Connection conn = null;
        try {
            conn = dbc.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MailDAO mailDAO = null;
        UserDAO userDAO = null;

        FriendsDAO friendsDAO = null;
        FriendRequestDAO friendRequestDAO = null;
        QuizDAO quizDAO = null;
        QuestionDAO questionDAO = null;
        try {
            userDAO = new UserDAO(conn);
            mailDAO = new MailDAO(conn);
            mailDAO.initialize();
            friendsDAO = new FriendsDAO(conn);
            friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
            quizDAO = new RealQuizDAO(conn);
            questionDAO = new RealQuestionDAO(conn);
            questionDAO.initialize();
            quizDAO.initialize();
         //   System.out.println(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("users", userDAO);

        servletContext.setAttribute("friends", friendsDAO);
        servletContext.setAttribute("friendRequests", friendRequestDAO);
        servletContext.setAttribute("quizzes", quizDAO);
        servletContext.setAttribute("questions", questionDAO);
        servletContext.setAttribute("mails", mailDAO);


    }
    public void contextDestroyed(ServletContextEvent event) {

    }
}
