package servlets.navigation;

import database.admin.AnnouncementDAO;
import database.database_connection.DatabaseConnector;
import classes.admin.Announcement;
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

@WebServlet("/AllAnnouncementsServlet")
public class AllAnnouncementsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);
            List<Announcement> announcements = announcementDAO.getAllAnnouncements();
            request.setAttribute("announcements", announcements);
            request.getRequestDispatcher("all-announcements.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading announcements", e);
        }
    }
}