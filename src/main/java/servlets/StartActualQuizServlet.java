package servlets;

import classes.Quiz;
import classes.Question;
import database.QuestionDAO;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;


@WebServlet("/StartActualQuizServlet")
public class StartActualQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("id");

        ServletContext context = getServletContext();
        HttpSession session = request.getSession();

        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");

        Quiz quiz = quizDAO.getQuiz(quizID);
        List<Question> questions = questionDAO.getQuiz(quizID);

        if (quiz == null || questions == null || questions.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found or has no questions.");
            return;
        }

        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);

        String format = (String) session.getAttribute("pageFormat:" + quizID);

        if ("single".equalsIgnoreCase(format)) {
            session.setAttribute("questionList", questions);
            session.setAttribute("currentIndex", 0);

            request.setAttribute("currentQuestion", questions.get(0));
            request.setAttribute("questionIndex", 0);
            request.setAttribute("totalQuestions", questions.size());
            request.getRequestDispatcher("questionPage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        }

    }
}
