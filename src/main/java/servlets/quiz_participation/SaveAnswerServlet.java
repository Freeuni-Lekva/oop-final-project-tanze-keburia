package servlets.quiz_participation;


import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/saveAnswer")
public class SaveAnswerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        HttpSession session = request.getSession();
        String questionId = request.getParameter("questionId");
        String userInput = request.getParameter("userAnswer");
        String action = request.getParameter("action");

        if(questionId == null || userInput == null || userInput.trim().isEmpty()) {
            response.sendRedirect("questionPage.jsp");
            return;
        }

        Map<String, GeneralAnswer> savedAnswers = (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");

        if(savedAnswers == null) {
            savedAnswers = new HashMap<>();
        }

        savedAnswers.put(questionId, new SingleAnswer(questionId, userInput.trim()));
        session.setAttribute("savedAnswers", savedAnswers);

        session.setAttribute("customMessage", action.equals("customize") ? "Answer customized!" : "Answer saved!");
        response.sendRedirect("questionPage.jsp");
    }
}
