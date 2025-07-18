package servlets.quiz_participation;


import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.history.QuizHistoryDAO;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;
import database.quiz_utilities.RealQuestionDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


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
        try(Connection connection = DatabaseConnector.getInstance().getConnection()){
            QuizDAO quizDAO = new RealQuizDAO(connection);
            QuestionDAO questionDAO= new RealQuestionDAO(connection);
            QuizHistoryDAO history = new QuizHistoryDAO(connection, quizDAO);
            System.out.println(quizId);
            Quiz quiz = quizDAO.getQuiz(quizId);
            if (quiz == null) {
                response.sendRedirect("viewAllQuizzes");
                return;
            }

            int questionCount = questionDAO.getQuiz(quizId).size();
            System.out.println(questionCount);
            request.setAttribute("quiz", quiz);
            request.setAttribute("leaderboard", history.getResultsByQuiz(quizId));
            request.setAttribute("questionCount", questionCount);
            request.getRequestDispatcher("/WEB-INF/startQuiz.jsp").forward(request, response);
        }catch(SQLException e) {
            throw new RuntimeException("Could not connect database");
        }
    }

}
