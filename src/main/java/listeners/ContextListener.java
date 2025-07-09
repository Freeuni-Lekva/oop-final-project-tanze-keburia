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

<<<<<<< Updated upstream
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
=======
        dbc = DatabaseConnector.getInstance();


        Connection conn = null;
        try {
            conn = dbc.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MailDAO mailDAO = null;
        UserDAO userDAO = null;
        AnnouncementDAO announcementDAO = null;
        FriendsDAO friendsDAO = null;
        FriendRequestDAO friendRequestDAO = null;
        QuizDAO quizDAO = null;
        QuestionDAO questionDAO = null;
        try {
            userDAO = new UserDAO(conn);
            mailDAO = new MailDAO(conn);
            mailDAO.initialize();
            announcementDAO = new AnnouncementDAO(conn);
            announcementDAO.initialize();
            friendsDAO = new FriendsDAO(conn);
            friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
            quizDAO = new RealQuizDAO(conn);
            questionDAO = new RealQuestionDAO(conn);
            questionDAO.initialize();
>>>>>>> Stashed changes
            quizDAO.initialize();
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            questionDAO.initialize();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
<<<<<<< Updated upstream
=======
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("users", userDAO);
        servletContext.setAttribute("announcements", announcementDAO);
        servletContext.setAttribute("friends", friendsDAO);
        servletContext.setAttribute("friendRequests", friendRequestDAO);
        servletContext.setAttribute("quizzes", quizDAO);
        servletContext.setAttribute("questions", questionDAO);
        servletContext.setAttribute("mails", mailDAO);


>>>>>>> Stashed changes
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

}