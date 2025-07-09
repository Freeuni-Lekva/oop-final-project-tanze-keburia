package servlets;

import classes.Admins;
import classes.Announcement;
import database.AnnouncementDAO;
import database.UserDAO;
import database.quiz_utilities.QuizDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

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
            // Get DAOs from servlet context
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");
            UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users");
            QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");

            // Get all announcements for display
            List<Announcement> announcements = announcementDAO.getAllAnnouncements();

            // Get site statistics
            int totalQuizzes = quizDAO.getNumQuizes();
            // For user count, we'll need to add a method to UserDAO
            // For now, we'll set it to 0 or implement a counter
            int totalUsers = 0; // This would need getUserCount() method in UserDAO

            // Set attributes for JSP
            request.setAttribute("announcements", announcements);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalQuizzes", totalQuizzes);
            request.setAttribute("adminUsername", username);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin-dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}