package listeners;

import database.*;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DatabaseConnector dbc = DatabaseConnector.getInstance();

        try (Connection conn = dbc.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            userDAO.initialize();
            MailDAO mailDAO = new MailDAO(conn);
            mailDAO.initialize();
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            friendsDAO.initialize();
            FriendRequestDAO friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
            friendRequestDAO.initialize();
            QuizDAO quizDAO = new RealQuizDAO(conn);
            quizDAO.initialize();
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            questionDAO.initialize();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

}