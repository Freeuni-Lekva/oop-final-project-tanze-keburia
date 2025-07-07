package servlets;

import classes.quiz_utilities.Question;
import classes.quiz_utilities.RealQuestion;
import database.quiz_utilities.QuestionDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/SubmitTextQuestionServlet")
public class SubmitTextQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");

        String quizID = request.getParameter("quizID");
        String statement = request.getParameter("statement");
        String answer = request.getParameter("answer");
        String pointsStr = request.getParameter("points");
        String questionID = request.getParameter("questionID");

        if (quizID == null || quizID.trim().isEmpty() ||
                statement == null || statement.trim().isEmpty() ||
                answer == null || answer.trim().isEmpty() ||
                pointsStr == null || pointsStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty form data.");
            return;
        }

        double points;
        try {
            points = Double.parseDouble(pointsStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid point format.");
            return;
        }

        if (questionID == null || questionID.isEmpty()) {
            questionID = UUID.randomUUID().toString();
            Question question = new RealQuestion(statement, answer, quizID, questionID, pointsStr);
            questionDAO.addQuestion(question);
        } else {
            Question question = new RealQuestion(statement, answer, quizID, questionID, pointsStr);
            questionDAO.modifyQuestion(question);
        }


        response.sendRedirect("GoBackToQuiz?quizID=" + quizID);
    }
}
