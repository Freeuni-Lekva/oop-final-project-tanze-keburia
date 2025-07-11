package servlets.quiz_management.question_management;

import Validation.OwnershipChecker;

import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.questions.RealQuestion;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;
import mapper.TypePageMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
        String jsp =  TypePageMapper.fromName(type).getJspPage();
        String quizID = request.getParameter("quizID");
        if(quizID == null || type == null || jsp == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        String id = UUID.randomUUID().toString();
        Question question = new RealQuestion("", "", quizID, id, "1");
        ServletContext context = getServletContext();
        QuestionDAO questionDAO = null;
        QuizDAO quizDAO = null;
        try (Connection conn = DatabaseConnector.getInstance().getConnection()){
            questionDAO = new RealQuestionDAO(conn);
            quizDAO = new RealQuizDAO(conn);

            if(questionDAO == null || quizDAO == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Initialization error");
                return;
            }

            if(!OwnershipChecker.checkOwnershipByID(quizDAO, request, response, quizID)){
                return;
            }
            System.out.println(question.getID());
            questionDAO.addQuestion(question);
            System.out.println(questionDAO.getAllQuestions().size());
            request.setAttribute("question", question);
            request.getRequestDispatcher(jsp+"?id="+id+"&quizID="+quizID).forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
