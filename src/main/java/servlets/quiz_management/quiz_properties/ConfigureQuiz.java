package servlets.quiz_management.quiz_properties;

import Validation.OwnershipChecker;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
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

@WebServlet("/ConfigureQuiz")
public class ConfigureQuiz extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            response.sendRedirect("login.jsp");
            return;
        }

        String quizID = request.getParameter("id");
        if(quizID == null || quizID.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            QuizDAO quizDAO = new RealQuizDAO(conn);

            Quiz quiz = quizDAO.getQuiz(quizID);
            if(quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            // Check ownership
            String username = (String)session.getAttribute("username");
            if(!OwnershipChecker.checkOwnershipByQuiz(quiz, request, response, username)) {
                return;
            }

            List<Question> questions = questionDAO.getQuiz(quizID);

            // Set attributes for JSP
            request.setAttribute("quizName", quiz.getName());
            request.setAttribute("questions", questions);
            request.setAttribute("id", quizID);
            request.setAttribute("quiz", quiz);

            request.getRequestDispatcher("configureQuiz.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}