package servlets;

import classes.Quiz;
import database.DatabaseConnector;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;



@WebServlet("/viewAllQuizzes")
public class ViewAllQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");

        List<Quiz> quizzes = quizDAO.getAll();
        request.setAttribute("quizzes", quizzes);
        request.getRequestDispatcher("/WEB-INF/allQuizzes.jsp").forward(request, response);

    }
}
