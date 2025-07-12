package servlets.quiz_participation;


import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


// for one-question-at-a-time type quiz to go to next question
@WebServlet("/nextQuestion")
public class NextQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        List<Question> questions = (List<Question>) session.getAttribute("questionList");

        if (currentIndex != null && questions != null && currentIndex < questions.size() - 1) {
            currentIndex++;
            session.setAttribute("currentIndex", currentIndex);

            if (quiz.getType().equals("FillBlank")) {
                Question currentQuestion = questions.get(currentIndex);
                request.setAttribute("question", currentQuestion);

                try {
                    request.getRequestDispatcher("takeFillBlankPerPage.jsp").forward(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }

        response.sendRedirect("questionPage.jsp");
    }

}
