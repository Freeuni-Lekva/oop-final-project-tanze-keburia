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
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get DAOs from servlet context
            UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users"); // Note: "users" not "userDAO"
            QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");

            // Initialize counts
            int totalUsers = userDAO != null ? userDAO.getUserCount() : 0;
            int totalQuizzes = quizDAO != null ? quizDAO.getAll().size() : 0;
            List<Announcement> announcements = announcementDAO != null ? announcementDAO.getAllAnnouncements() : new ArrayList<>();

            // Get user count with connection verification
            if (userDAO != null) {
                try {
                    totalUsers = userDAO.getUserCount();
                    System.out.println("[DEBUG] Current user count: " + totalUsers); // Debug log
                } catch (Exception e) {
                    System.err.println("Error getting user count: " + e.getMessage());
                }
            }

            // Get quiz count
            if (quizDAO != null) {
                try {
                    totalQuizzes = quizDAO.getAll().size();
                } catch (Exception e) {
                    System.err.println("Error getting quiz count: " + e.getMessage());
                }
            }

            // Set attributes for JSP
            request.setAttribute("adminUsername", username);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalQuizzes", totalQuizzes);
            request.setAttribute("announcements", announcements); // Add this line
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            // Fallback values if error occurs
            request.setAttribute("adminUsername", username);
            request.setAttribute("totalUsers", 0);
            request.setAttribute("totalQuizzes", 0);
            request.setAttribute("error", "Could not load statistics: " + e.getMessage());

            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
}