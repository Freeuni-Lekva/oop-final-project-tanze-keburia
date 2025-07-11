package servlets.navigation;

import classes.mail.Mail;
import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;
import database.social.MailDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/Homepage")
public class HomepageServlet extends HttpServlet {
    private static final int DISPLAY_LIMIT = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            MailDAO mailDAO = new MailDAO(conn);
            List<Mail> inboxPreview = mailDAO.getInbox(username);
            request.setAttribute("inboxPreview", inboxPreview);

            QuizDAO quizDAO = new RealQuizDAO(conn);

            int totalQuizCount = quizDAO.getNumQuizes();
            int limit = Math.min(DISPLAY_LIMIT, totalQuizCount);

            List<Quiz> recentQuizzes = quizDAO.getRecentQuizzes(limit);
            List<Quiz> popularQuizzes = quizDAO.getPopularQuizzes(limit);

            request.setAttribute("recentQuizzes", recentQuizzes);
            request.setAttribute("popularQuizzes", popularQuizzes);
        } catch (Exception e) {
            throw new ServletException("Error loading inbox", e);
        }

        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }
}
