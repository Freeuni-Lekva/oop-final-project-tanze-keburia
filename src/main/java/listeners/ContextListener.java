package listeners;

import classes.User;
import database.admin.Admins;
import database.admin.AnnouncementDAO;
import database.achievement.AchievementDAO;
import database.database_connection.DatabaseConnector;
import database.history.QuizHistoryDAO;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;
import database.social.ChallengeDAO;
import database.social.FriendRequestDAO;
import database.social.FriendsDAO;
import database.social.MailDAO;
import database.user.UserDAO;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        final String DEFAULT_ADMIN_USERNAME = "admin";
        final String DEFAULT_ADMIN_PASSWORD = "123";


        try (Connection conn = dbc.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            userDAO.initialize();


            if (!userDAO.userExists(DEFAULT_ADMIN_USERNAME)) {
                User adminUser = new User(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD);
                userDAO.addUser(adminUser);
                userDAO.setAdminStatus(DEFAULT_ADMIN_USERNAME, true);
                Admins.addAdmin(DEFAULT_ADMIN_USERNAME);
                System.out.println("Created default admin user: " + DEFAULT_ADMIN_USERNAME);
            } else if (!Admins.isAdmin(DEFAULT_ADMIN_USERNAME)) {
                userDAO.setAdminStatus(DEFAULT_ADMIN_USERNAME, true);
                Admins.addAdmin(DEFAULT_ADMIN_USERNAME);
                System.out.println("Updated existing user to admin: " + DEFAULT_ADMIN_USERNAME);
            }

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
            QuizHistoryDAO quizHistory = new QuizHistoryDAO(conn, quizDAO);
            quizHistory.initialize();
            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);
            announcementDAO.initialize();
            ChallengeDAO cdao =new ChallengeDAO(conn);
            cdao.initialize();
            AchievementDAO achievementDAO = new AchievementDAO(conn);
            achievementDAO.initialize();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

}