package servlets.admin;


import database.admin.Admins;
import classes.quiz_utilities.quiz.Quiz;
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
import java.util.List;

@WebServlet("/AdminQuizzesServlet")
public class AdminQuizzesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            List<Quiz> quizzes = quizDAO.getAll();

            request.setAttribute("quizzes", quizzes);
            request.setAttribute("adminUsername", username);
            request.getRequestDispatcher("admin-quizzes.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading quizzes", e);
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
        String quizId = request.getParameter("quizId");

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);

            if ("deleteQuiz".equals(action) && quizId != null) {
                Quiz quiz = quizDAO.getQuiz(quizId);
                if (quiz != null) {
                    quizDAO.removeQuiz(quiz);
                    request.setAttribute("success", "Quiz deleted");
                }
            } else if ("clearHistory".equals(action) && quizId != null) {
                request.setAttribute("success", "Quiz history cleared");
            }
            response.sendRedirect("AdminQuizzesServlet");

        } catch (SQLException e) {
            throw new ServletException("Error processing request", e);
        }
    }
}