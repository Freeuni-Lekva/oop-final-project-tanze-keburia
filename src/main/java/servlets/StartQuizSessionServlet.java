package servlets;


import classes.Question;
import classes.Quiz;
import database.QuestionDAO;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


// for one-question-at-a-time type quiz
@WebServlet("/startQuizSession")
public class StartQuizSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("id");

        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");

        List<Question> questions = questionDAO.getQuiz(quizID);

        HttpSession session = request.getSession();
        session.setAttribute("quizID", quizID);
        session.setAttribute("questions", questions);
        session.setAttribute("currentIndex", 0);

        response.sendRedirect("questionPage.jsp");
    }
}
