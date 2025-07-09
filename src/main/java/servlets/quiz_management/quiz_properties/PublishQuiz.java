package servlets;

import Validation.OwnershipChecker;
import classes.quiz_utilities.Quiz;
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


@WebServlet("/PublishQuiz")
public class PublishQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ServletContext context = getServletContext();
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        QuizDAO quizzes = null;
        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            quizzes = new RealQuizDAO(connection);

            String quizID =  request.getParameter("quizID");
            String quizTopic = request.getParameter("topic");
            Quiz quiz = quizzes.getQuiz(quizID);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }
            if(!OwnershipChecker.checkOwnershipByQuiz(quiz, request, response, (String)session.getAttribute("username"))) {
                return;
            }
            quiz.setTopic(quizTopic);
            quiz.setVisible(true);
            quizzes.modifyQuiz(quiz);
            response.sendRedirect("Homepage");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
