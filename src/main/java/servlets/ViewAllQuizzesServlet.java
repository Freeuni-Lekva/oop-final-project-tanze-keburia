package servlets;

import classes.Quiz;
import database.DatabaseConnector;
import database.QuizDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.RealQuizDAO;

@WebServlet("/viewAllQuizzes")
public class ViewAllQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            List<Quiz> quizzes = quizDAO.getAll();
            request.setAttribute("quizzes", quizzes);
            request.getRequestDispatcher("/WEB-INF/allQuizzes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Can't load quizzes", e);
        }

    }
}
