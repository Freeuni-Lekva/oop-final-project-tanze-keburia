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
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");

        Quiz quiz = quizDAO.getQuiz(quizID);
        List<Question> questions = questionDAO.getQuiz(quizID);

        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);

        request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);

    }
}
