package servlets;

import Validation.OwnershipChecker;
import classes.MockQuestion;
import classes.Question;
import classes.Quiz;
import database.QuestionDAO;
import database.QuizDAO;
import mapper.TypePageMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/AddQuestion")
public class AddQuestion extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null){
           response.sendRedirect("login.jsp");
           return;
        }
        String type =  request.getParameter("type");
        String jsp =  TypePageMapper.getPageForType(type);
        String quizID = request.getParameter("quizID");
        if(quizID == null || type == null || jsp == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }
        System.out.println(quizID);
        String id = UUID.randomUUID().toString();
        Question question = new MockQuestion("", "", quizID, id);
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizzes");
        if(questionDAO == null || quizDAO == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Initialization error");
            return;
        }
        if(!OwnershipChecker.checkOwnership(quizDAO, request, response, quizID)){
            return;
        }
        questionDAO.addQuestion(question);
        System.out.println(question.getStatement());
        context.setAttribute("questions", questionDAO);
        response.sendRedirect(jsp+"?id="+id+"&quizID="+quizID);
    }
}
