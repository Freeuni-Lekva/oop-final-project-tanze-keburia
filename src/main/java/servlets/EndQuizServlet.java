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
import java.util.Map;

@WebServlet("/endQuiz")
public class EndQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz not found in session.");
            return;
        }

        QuestionDAO questionDAO = (QuestionDAO) getServletContext().getAttribute("questions");
        if (questionDAO == null) {
            throw new ServletException("QuestionDAO not found in context.");
        }

        List<Question> questions = questionDAO.getQuiz(quiz.getID());
        TextAnswerChecker checker = new TextAnswerChecker(questionDAO);

        double totalScore = 0;

        String format = quiz.getPageFormat(); // assume this is "one-pager" or "one-at-a-time"

        if ("All Questions on One Page".equalsIgnoreCase(format)) {
            for (Question q : questions) {
                String paramName = "answer_" + q.getID();
                String userInput = request.getParameter(paramName);

                if (userInput != null && !userInput.trim().isEmpty()) {
                    GeneralAnswer userAnswer = new SingleAnswer(q.getID(), userInput.trim());
                    totalScore += checker.getPoints(q.getID(), userAnswer);
                }
            }

        } else if ("One Question at a Time".equalsIgnoreCase(format)) {
            Map<String, GeneralAnswer> savedAnswers =
                    (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");

            if (savedAnswers != null) {
                for (Question q : questions) {
                    GeneralAnswer answer = savedAnswers.get(q.getID());
                    if (answer != null) {
                        totalScore += checker.getPoints(q.getID(), answer);
                    }
                }
            }
        }



        String submittedDueToTimeout = request.getParameter("submittedDueToTimeout");
        Long startTimeObj = (Long) session.getAttribute("quizStartTime");
        Long endTime = System.currentTimeMillis();
        String timeTakenStr;

        if ("true".equals(submittedDueToTimeout)) {
            timeTakenStr = "Time limit exceeded";
        } else if (startTimeObj == null) {
            timeTakenStr = "N/A";
        } else {
            long timeTaken = (endTime - startTimeObj) / 1000;
            if (timeTaken < 60) {
                timeTakenStr = timeTaken + " seconds";
            } else {
                timeTakenStr = (timeTaken / 60) + " minutes and " + (timeTaken % 60) + " seconds";
            }
        }
        request.setAttribute("timeTaken", timeTakenStr);



        request.setAttribute("totalScore", totalScore);
        request.getRequestDispatcher("endQuiz.jsp").forward(request, response);
    }
}

