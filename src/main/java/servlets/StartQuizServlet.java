package servlets;


import classes.Quiz;
import database.DatabaseConnector;
import database.QuizDAO;
import database.RealQuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;


@WebServlet("/startQuiz")
public class StartQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("id");
        if (quizId == null) {
            response.sendRedirect("viewAllQuizzes");
            return;
        }

        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");

        Quiz quiz = quizDAO.getQuiz(quizId);
        if (quiz == null) {
            response.sendRedirect("viewAllQuizzes");
            return;
        }

        request.setAttribute("quiz", quiz);
        request.getRequestDispatcher("/WEB-INF/startQuiz.jsp").forward(request, response);
    }

}
