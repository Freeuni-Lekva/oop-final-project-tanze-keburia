package servlets;



import classes.quiz_utilities.Question;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;

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
import java.util.List;


// for one-question-at-a-time type quiz
@WebServlet("/startQuizSession")
public class StartQuizSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("id");

        ServletContext context = getServletContext();
        try(Connection conn =  DatabaseConnector.getInstance().getConnection()){

            QuestionDAO questionDAO = new RealQuestionDAO(conn);

            List<Question> questions = questionDAO.getQuiz(quizID);

            HttpSession session = request.getSession();
            session.setAttribute("quizID", quizID);
            session.setAttribute("questions", questions);
            session.setAttribute("currentIndex", 0);

            response.sendRedirect("questionPage.jsp");
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
