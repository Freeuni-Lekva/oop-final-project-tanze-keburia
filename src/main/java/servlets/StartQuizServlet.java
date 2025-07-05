package servlets;


import classes.quiz_utilities.Quiz;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/startQuiz")
public class StartQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("id");
        if (quizId == null) {
            response.sendRedirect("viewAllQuizzes");
            return;
        }

        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");

        Quiz quiz = quizDAO.getQuiz(quizId);
        if (quiz == null) {
            response.sendRedirect("viewAllQuizzes");
            return;
        }

        int questionCount = questionDAO.getQuiz(quizId).size();
        request.setAttribute("quiz", quiz);
        request.setAttribute("questionCount", questionCount);
        request.getRequestDispatcher("/WEB-INF/startQuiz.jsp").forward(request, response);
    }

}
