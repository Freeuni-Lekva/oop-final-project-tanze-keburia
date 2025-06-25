package servlets;

import Validation.OwnershipChecker;
import classes.MockQuestion;
import classes.Question;
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
@WebServlet("/ModifyQuestion")
public class ModifyQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String currentUser = (String) session.getAttribute("user");
        String questionId = request.getParameter("questionID");
        String quizId = request.getParameter("quizID");
        String answer = request.getParameter("answer");
        String statement = request.getParameter("statement");
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quiz");
        if(!OwnershipChecker.checkOwnership(quizDAO, request, response, quizId)) {
            return;
        }
        Question question = questionDAO.getQuestion(questionId);
        question.setAnswer(answer);
        question.setStatement(statement);
        questionDAO.modifyQuestion(question);
        response.sendRedirect("configureQuiz.jsp?id="+quizId);
    }
}
