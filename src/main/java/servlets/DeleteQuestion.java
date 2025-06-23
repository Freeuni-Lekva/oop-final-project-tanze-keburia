package servlets;

import classes.Question;
import database.QuestionDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/DeleteQuestion")
public class DeleteQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("questionID");
        ServletContext servletContext = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) servletContext.getAttribute("questions");
        Question q = questionDAO.getQuestion(id);
        questionDAO.removeQuestion(q);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
