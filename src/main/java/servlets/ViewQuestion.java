package servlets;

import Validation.OwnershipChecker;
import classes.quiz_utilities.Question;
import classes.quiz_utilities.Quiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;
import mapper.TypePageMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ViewQuestion")
public class ViewQuestion extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
        }
        String id = request.getParameter("id");
        String quizID = request.getParameter("quizID");
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            if(!OwnershipChecker.checkOwnershipByID(quizDAO, request, response, quizID)){
               return;
            }
            Question q = questionDAO.getQuestion(id);
            Quiz x = quizDAO.getQuiz(quizID);
            request.setAttribute("question", q);
            request.setAttribute("id", id);
            request.setAttribute("quizID", quizID);
            String pageURL = TypePageMapper.fromName(x.getType()).getJspPage();
            request.getRequestDispatcher(pageURL).forward(request, response);
        }catch(SQLException e) {
            throw new ServletException(e);
        }
    }
}
