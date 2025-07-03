package servlets;

import classes.MockQuiz;
import classes.Quiz;
import classes.RealQuiz;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        QuizDAO quizDAO = (QuizDAO) servletContext.getAttribute("quizzes");
       // Integer numQuizes = (Integer)(servletContext.getAttribute("numQuizes"));
        String username = session.getAttribute("username").toString();
        Date now = new Date();
        String id = UUID.randomUUID().toString();
        Quiz newQuiz = new RealQuiz(username, now, id, request.getParameter("type"), request.getParameter("quizName"));
        quizDAO.addQuiz(newQuiz);
        response.sendRedirect("configureQuiz.jsp?id=" + id);
    }
}
