package servlets.admin;


import database.admin.Admins;
import classes.admin.Announcement;
import database.admin.AnnouncementDAO;
import database.user.UserDAO;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            QuizDAO quizDAO = new RealQuizDAO(conn);
            AnnouncementDAO announcementDAO = new AnnouncementDAO(conn);

            int totalUsers = userDAO.getUserCount();
            int totalQuizzes = quizDAO.getAll().size();
            List<Announcement> announcements = announcementDAO.getAllAnnouncements();

            request.setAttribute("adminUsername", username);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalQuizzes", totalQuizzes);
            request.setAttribute("announcements", announcements);

            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("adminUsername", username);
            request.setAttribute("totalUsers", 0);
            request.setAttribute("totalQuizzes", 0);
            request.setAttribute("announcements", new ArrayList<>());
            request.setAttribute("error", "Could not load statistics: " + e.getMessage());

            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
}