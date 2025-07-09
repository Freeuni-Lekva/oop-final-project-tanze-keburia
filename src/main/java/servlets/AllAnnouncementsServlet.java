package servlets;

import classes.Announcement;
import database.AnnouncementDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AllAnnouncementsServlet")
public class AllAnnouncementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        try {
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");
            List<Announcement> allAnnouncements = announcementDAO.getAllAnnouncements();

            request.setAttribute("allAnnouncements", allAnnouncements);
            request.setAttribute("username", username);

            request.getRequestDispatcher("allAnnouncements.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading announcements", e);
        }
    }
}