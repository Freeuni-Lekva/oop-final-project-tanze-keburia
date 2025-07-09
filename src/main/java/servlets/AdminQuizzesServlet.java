package servlets;

import classes.Admins;
import classes.quiz_utilities.Quiz;
import database.quiz_utilities.QuizDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminQuizzesServlet")  // Changed to match the URL in your error

public class AdminQuizzesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");
            List<Quiz> quizzes = quizDAO.getAll();

            request.setAttribute("quizzes", quizzes);
            request.setAttribute("adminUsername", username);

            request.getRequestDispatcher("admin-quizzes.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error loading quizzes", e);
        }
    }
}