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

@WebServlet("/SetTimeLimit")
public class SetTimeLimit extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String quizId = request.getParameter("quizID");
        String timeLimit = request.getParameter("timeLimit");
        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        if(!OwnershipChecker.checkOwnershipByID(quizDAO, request, response, quizId)){
            return;
        }
        if(timeLimit == null || timeLimit.isEmpty()){
            return;
        }
        int x = Integer.parseInt(timeLimit);
        Quiz quiz = quizDAO.getQuiz(quizId);
        quiz.setTimeLimit(x);
        quizDAO.modifyQuiz(quiz);
        String ref = request.getHeader("Referer");
       // System.out.println(ref);
        response.sendRedirect(ref);
    }
}
