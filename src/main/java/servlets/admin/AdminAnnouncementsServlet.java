package servlets.admin;

import database.admin.Admins;
import classes.admin.Announcement;
import database.admin.AnnouncementDAO;
import database.database_connection.DatabaseConnector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/AdminAnnouncementServlet")
public class AdminAnnouncementsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);
            request.setAttribute("announcements", announcementDAO.getAllAnnouncements());
            request.setAttribute("adminUsername", username);

            request.getRequestDispatcher("admin-announcements.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading announcements", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);

            if ("create".equals(action)) {
                String body = request.getParameter("body");
                if (body != null && !body.trim().isEmpty()) {
                    Announcement announcement = new Announcement(
                            UUID.randomUUID().toString(),
                            username,
                            body,
                            new Timestamp(System.currentTimeMillis())
                    );
                    announcementDAO.addAnnouncement(announcement);
                }
            } else if ("delete".equals(action)) {
                String announcementId = request.getParameter("announcementId");
                if (announcementId != null) {
                    announcementDAO.removeAnnouncement(announcementId);
                }
            }
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            response.sendRedirect("AdminAnnouncementServlet?t=" + System.currentTimeMillis());


        } catch (SQLException e) {
            throw new ServletException("Error processing announcement", e);
        }
    }
}