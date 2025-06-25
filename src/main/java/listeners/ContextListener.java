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
        QuizDAO quizDAO = null;
        QuestionDAO questionDAO = null;
        try {
            userDAO = new UserDAO(conn);
            friendsDAO = new FriendsDAO(conn);
            friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
            quizDAO = new MockQuizDAO(conn);
            questionDAO = new MockQuestionDAO(conn);
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
        Integer numQuizes = 0;
        Integer numQuestions = 0;
        servletContext.setAttribute("numQuizzes", numQuizes);
        servletContext.setAttribute("numQuestions", numQuestions);
    }
    public void contextDestroyed(ServletContextEvent event) {

    }
}
