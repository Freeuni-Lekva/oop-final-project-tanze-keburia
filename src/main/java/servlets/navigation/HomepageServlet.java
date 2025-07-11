package servlets.navigation;

import database.admin.Admins;
import classes.admin.Announcement;
import classes.achievement.Achievement;
import classes.mail.Mail;
import database.achievement.AchievementDAO;
import database.database_connection.DatabaseConnector;
import database.admin.AnnouncementDAO;
import database.social.MailDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Homepage")
public class HomepageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            // Force autocommit to ensure we see latest data
            conn.setAutoCommit(true);

            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);
            MailDAO mailDAO = new MailDAO(conn);

            Announcement latestAnnouncement = announcementDAO.getLatestAnnouncement();
            List<Mail> inboxPreview = mailDAO.getInbox(username);

            request.setAttribute("latestAnnouncement", latestAnnouncement);
            request.setAttribute("inboxPreview", inboxPreview);
            AchievementDAO achievementDAO = new AchievementDAO(conn);
            List<Achievement> achievements = achievementDAO.getUserAchievements(username);
            request.setAttribute("achievements", achievements);

            if (Admins.isAdmin(username)) {
                request.getRequestDispatcher("adminHomepage.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("homepage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error loading homepage data", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}