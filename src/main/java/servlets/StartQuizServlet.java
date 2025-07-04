package servlets;


import classes.Quiz;
import database.DatabaseConnector;
import database.QuizDAO;
import database.RealQuizDAO;

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
        String quizID = request.getParameter("id");

        if(quizID == null || quizID.isEmpty()) {
            response.sendRedirect("homepage.jsp");
            return;
        }

        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            Quiz quiz = quizDAO.getQuiz(quizID);

            if(quiz == null) {
                response.sendRedirect("homepage.jsp");
                return;
            }

            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("/WEB-INF/startQuiz.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Can't load quizzes", e);
        }
    }

}
