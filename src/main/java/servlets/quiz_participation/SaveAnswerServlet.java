package servlets.quiz_participation;

import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.quiz.Quiz;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        String questionId = request.getParameter("questionId");
        String userInput = request.getParameter("userAnswer");
        String action = request.getParameter("action");

        if (questionId == null || userInput == null || userInput.trim().isEmpty()) {
            redirectToQuestionPage(response, quiz);
            return;
        }

        Map<String, GeneralAnswer> savedAnswers = (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");
        if (savedAnswers == null) savedAnswers = new HashMap<>();
        savedAnswers.put(questionId, new SingleAnswer(questionId, userInput.trim()));
        session.setAttribute("savedAnswers", savedAnswers);

        // update currentIndex
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        if (currentIndex == null) currentIndex = 0;

        if ("next".equals(action)) {
            session.setAttribute("currentIndex", currentIndex + 1);
            redirectToQuestionPage(response, quiz);
        } else if ("prev".equals(action)) {
            session.setAttribute("currentIndex", Math.max(currentIndex - 1, 0));
            redirectToQuestionPage(response, quiz);
        } else {
            // "end"
            response.sendRedirect("endQuiz");
        }
    }

    private void redirectToQuestionPage(HttpServletResponse response, Quiz quiz) throws IOException {
        if (quiz != null && "FillBlank".equals(quiz.getType())) {
            response.sendRedirect("takeFillBlankPerPage.jsp");
        } else {
            response.sendRedirect("questionPage.jsp");
        }
    }
}
