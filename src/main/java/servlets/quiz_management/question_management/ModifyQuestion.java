package servlets;

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

@WebServlet("/ModifyQuestion")
public class ModifyQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String currentUser = (String) session.getAttribute("user");
        String questionId = request.getParameter("questionID");
        String quizId = request.getParameter("quizID");
        String answer = request.getParameter("answer");
        String statement = request.getParameter("statement");
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = null;
        QuizDAO quizDAO = null;
        try(Connection connection = DatabaseConnector.getInstance().getConnection()) {
            questionDAO = new RealQuestionDAO(connection);
            quizDAO = new RealQuizDAO(connection);

            if(questionDAO == null || quizDAO == null) {
               // throw new ServletException("QuestionDAO or QuizDAO NULL");
                response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            }
           // QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
            if(!OwnershipChecker.checkOwnershipByID(quizDAO, request, response, quizId)) {
                return;
            }
            Question question = questionDAO.getQuestion(questionId);
            question.setAnswer(answer);
            question.setStatement(statement);
            questionDAO.modifyQuestion(question);
            response.sendRedirect("ConfigureQuiz?id="+quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
