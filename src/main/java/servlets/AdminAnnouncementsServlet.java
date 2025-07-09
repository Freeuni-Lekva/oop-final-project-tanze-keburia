package servlets;
import classes.Admins;
import classes.Announcement;
import database.AnnouncementDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/admin-announcements")
public class AdminAnnouncementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");

            // Get all announcements
            request.setAttribute("announcements", announcementDAO.getAllAnnouncements());
            request.setAttribute("adminUsername", username);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin-announcements.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");

        try {
            if ("create".equals(action)) {
                String body = request.getParameter("body");
                if (body != null && !body.trim().isEmpty()) {
                    String announcementId = UUID.randomUUID().toString();
                    Timestamp now = new Timestamp(System.currentTimeMillis());

                    Announcement announcement = new Announcement(announcementId, username, body, now);
                    announcementDAO.addAnnouncement(announcement);

                    request.setAttribute("success", "Announcement created successfully!");
                }
            } else if ("delete".equals(action)) {
                String announcementId = request.getParameter("announcementId");
                if (announcementId != null) {
                    announcementDAO.removeAnnouncement(announcementId);
                    request.setAttribute("success", "Announcement deleted successfully!");
                }
            }

            // Redirect to avoid form resubmission
            response.sendRedirect("admin-announcements");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred");
            doGet(request, response);
        }
    }
}