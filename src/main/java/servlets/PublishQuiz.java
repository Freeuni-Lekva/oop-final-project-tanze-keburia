package servlets;

import Validation.OwnershipChecker;
import classes.Quiz;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet("/PublishQuiz")
public class PublishQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        QuizDAO quizzes = (QuizDAO)context.getAttribute("quizzes");
        String quizID =  request.getParameter("quizID");
        String quizTopic = request.getParameter("topic");
        Quiz quiz = quizzes.getQuiz(quizID);
        if(!OwnershipChecker.checkOwnershipByQuiz(quiz, request, response, (String)session.getAttribute("username"))) {
            return;
        }
        quiz.setTopic(quizTopic);
        quiz.setVisible(true);
        quizzes.modifyQuiz(quiz);
        response.sendRedirect("homepage.jsp");

    }
}
