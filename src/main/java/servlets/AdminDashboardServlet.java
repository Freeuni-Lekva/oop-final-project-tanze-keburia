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

@WebServlet("/AdminDashboardServlet")  // Changed to match your JSP
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
            AnnouncementDAO announcementDAO = (AnnouncementDAO) getServletContext().getAttribute("announcements");
            QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");

            List<Announcement> announcements = announcementDAO.getAllAnnouncements();
            int totalQuizzes = quizDAO.getNumQuizes();

            request.setAttribute("announcements", announcements);
            request.setAttribute("totalQuizzes", totalQuizzes);
            request.setAttribute("adminUsername", username);

            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}