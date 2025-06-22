package servlets;

import classes.MockQuestion;
import classes.Question;
import database.QuestionDAO;
import mapper.TypePageMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/AddQuestion")
public class AddQuestion extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type =  request.getParameter("type");
        String jsp =  TypePageMapper.getPageForType(type);
        String quizID = request.getParameter("quizID");
        System.out.println(quizID);
        String id = UUID.randomUUID().toString();
        Question question = new MockQuestion("-", "-", quizID, id);
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
        questionDAO.addQuestion(question);
        System.out.println(question.getStatement());
        context.setAttribute("questions", questionDAO);
        response.sendRedirect(jsp+"?id="+id+"&quizID="+quizID);
    }
}
