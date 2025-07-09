package servlets.quiz_management.question_management;

import Validation.OwnershipChecker;
import classes.quiz_utilities.Question;
import database.*;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
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

@WebServlet("/DeleteQuestion")
public class DeleteQuestion extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null ||  session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String id = request.getParameter("questionID");
        String quizID = request.getParameter("quizID");
        ServletContext servletContext = getServletContext();
        QuizDAO quizDAO = null;
        QuestionDAO questionDAO = null;
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            quizDAO = new RealQuizDAO(connection);
            questionDAO = new RealQuestionDAO(connection);

            if(!OwnershipChecker.checkOwnershipByID(quizDAO, request, response, quizID)){
                return;
            }
            Question q = questionDAO.getQuestion(id);
            questionDAO.removeQuestion(q);
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
