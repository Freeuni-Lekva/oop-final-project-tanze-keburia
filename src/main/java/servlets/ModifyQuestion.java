package servlets;

import classes.MockQuestion;
import classes.Question;
import database.QuestionDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/ModifyQuestion")
public class ModifyQuestion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionId = request.getParameter("questionID");
        System.out.println(questionId);
        String quizId = request.getParameter("quizID");
        String answer = request.getParameter("answer");
        String statement = request.getParameter("statement");
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
        Question question = questionDAO.getQuestion(questionId);
        question.setAnswer(answer);
        question.setStatement(statement);
        questionDAO.modifyQuestion(question);
        response.sendRedirect("configureQuiz.jsp?id="+quizId);
    }
}
