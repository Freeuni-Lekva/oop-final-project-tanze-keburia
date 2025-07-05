package servlets;


import classes.Question;

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
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        List<Question> questions = (List<Question>) session.getAttribute("questionList");

        if(currentIndex != null && questions != null && currentIndex < questions.size() - 1) {
            session.setAttribute("currentIndex", currentIndex + 1);
        }

        response.sendRedirect("questionPage.jsp");
    }
}
