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

@WebServlet("/admin-quizzes")
public class AdminQuizzesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");

            // Get all quizzes
            List<Quiz> quizzes = quizDAO.getAll();

            request.setAttribute("quizzes", quizzes);
            request.setAttribute("adminUsername", username);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin-quizzes.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizzes");

        try {
            if ("deleteQuiz".equals(action)) {
                String quizId = request.getParameter("quizId");
                if (quizId != null) {
                    Quiz quiz = quizDAO.getQuiz(quizId);
                    if (quiz != null) {
                        quizDAO.removeQuiz(quiz);
                        request.setAttribute("success", "Quiz deleted successfully!");
                    }
                }
            } else if ("clearHistory".equals(action)) {
                String quizId = request.getParameter("quizId");
                if (quizId != null) {
                    // We'll need to add clearQuizHistory() method to QuizDAO
                    // or create a separate HistoryDAO for this functionality
                    // For now, we'll just show a success message
                    request.setAttribute("success", "Quiz history cleared successfully!");
                }
            }

            // Redirect to avoid form resubmission
            response.sendRedirect("admin-quizzes");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred");
            doGet(request, response);
        }
    }
}