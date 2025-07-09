package servlets;

import classes.Admins;
import classes.Announcement;
import classes.Mail;
import database.database_connection.DatabaseConnector;
import database.MailDAO;
import database.AnnouncementDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
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

        try {
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");
            Announcement latestAnnouncement = announcementDAO.getLatestAnnouncement();
            request.setAttribute("latestAnnouncement", latestAnnouncement);
            try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
                MailDAO mailDAO = new MailDAO(conn);
                List<Mail> inboxPreview = mailDAO.getInbox(username);
                request.setAttribute("inboxPreview", inboxPreview);
            }

            if (Admins.isAdmin(username)) {
                request.getRequestDispatcher("adminHomepage.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("homepage.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error loading inbox", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}