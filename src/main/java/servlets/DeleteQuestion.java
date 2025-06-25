package servlets;

import Validation.OwnershipChecker;
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
@WebServlet("/DeleteQuestion")
public class DeleteQuestion extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null ||  session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String id = request.getParameter("questionID");
        String quizID = request.getParameter("quizID");
        ServletContext servletContext = getServletContext();
        QuizDAO quizDAO = (QuizDAO) servletContext.getAttribute("quizzes");
        QuestionDAO questionDAO = (QuestionDAO) servletContext.getAttribute("questions");
        if(!OwnershipChecker.checkOwnership(quizDAO, request, response, quizID)){
            return;
        }
        Question q = questionDAO.getQuestion(id);
        questionDAO.removeQuestion(q);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
