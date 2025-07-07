package servlets;


import classes.quiz_utilities.*;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.RealQuestionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/endQuiz")
public class EndQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        QuestionDAO questionDAO = (QuestionDAO) getServletContext().getAttribute("questions");
        if (questionDAO == null) {
            throw new ServletException("QuestionDAO not found in context.");
        }

        List<Question> questions = questionDAO.getQuiz(quiz.getID());
        TextAnswerChecker checker = new TextAnswerChecker(questionDAO);

        double totalScore = 0;

        for (Question q : questions) {
            String questionId = q.getID();
            String paramName = "answer_" + questionId;
            String userInput = request.getParameter(paramName);

            if (userInput == null || userInput.trim().isEmpty()) continue;

            GeneralAnswer userAnswer = new SingleAnswer(questionId, userInput.trim());
            totalScore += checker.getPoints(questionId, userAnswer);
        }

        request.setAttribute("totalScore", totalScore);
        request.getRequestDispatcher("endQuiz.jsp").forward(request, response);
    }

}
