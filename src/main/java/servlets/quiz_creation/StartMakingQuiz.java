package servlets.quiz_creation;



import classes.quiz_utilities.quiz.Quiz;
import classes.quiz_utilities.quiz.RealQuiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuizDAO;
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
import java.util.Date;
import java.util.UUID;

@WebServlet("/StartMakingQuiz")
public class StartMakingQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("username") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please log in first.");
            return;
        }
        QuizDAO quizDAO = null;
        try(Connection conn = DatabaseConnector.getInstance().getConnection()){
       // Integer numQuizes = (Integer)(servletContext.getAttribute("numQuizes"));
            quizDAO = new RealQuizDAO(conn);
            String username = session.getAttribute("username").toString();
            Date now = new Date();
            String id = UUID.randomUUID().toString();
            String format = request.getParameter("format");
            Quiz newQuiz = new RealQuiz(username, now, id, request.getParameter("type"), request.getParameter("quizName"), format);
            quizDAO.addQuiz(newQuiz);

            response.sendRedirect("ConfigureQuiz?id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
