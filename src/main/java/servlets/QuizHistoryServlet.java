package servlets;
import classes.QuizResult;
import database.MockQuizHistoryDAO;
import database.QuizHistoryDAO;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;

import javax.servlet.ServletContext;
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

@WebServlet("/QuizHistoryServlet")
public class QuizHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ServletContext context = getServletContext();
        try (Connection conn = DatabaseConnector.getInstance().getConnection())
        {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            QuizHistoryDAO quizHistDAO = new QuizHistoryDAO(conn, quizDAO);
            List<QuizResult> quizzes = quizHistDAO.getUserHistory(username);
            request.setAttribute("History", quizzes);
            request.getRequestDispatcher("quizHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}