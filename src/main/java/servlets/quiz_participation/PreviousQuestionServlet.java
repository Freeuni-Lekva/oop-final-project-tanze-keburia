package servlets.quiz_participation;

import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.questions.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/PreviousQuestionServlet")
public class PreviousQuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Get current state
        int currentIndex = (Integer) session.getAttribute("currentIndex");
        List<Question> questions = (List<Question>) session.getAttribute("questionList");
        Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");

        // Store current answer
        String questionId = questions.get(currentIndex).getID();
        String answerText = request.getParameter("answer");
        userAnswers.put(questionId, new SingleAnswer(questionId, answerText));

        // Move to previous question
        if (currentIndex > 0) {
            session.setAttribute("currentIndex", currentIndex - 1);
        }
        response.sendRedirect("manyTextQuestions.jsp");
    }
}
