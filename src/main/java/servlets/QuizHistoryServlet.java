package servlets;

import classes.QuizResult;

import database.DatabaseConnector;
import database.FriendsDAO;
import database.QuizHistoryDAO;
import database.RealQuizDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


@WebServlet("/QuizHistoryServlet")
public class QuizHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // safer: false so no new session created
        if (session == null || session.getAttribute("username") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        String currentUser = (String) session.getAttribute("username");
        String targetUser = request.getParameter("username");

        if (targetUser == null || targetUser.isEmpty()) {
            targetUser = currentUser;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizHistoryDAO quizHistoryDAO = new QuizHistoryDAO(conn);
            RealQuizDAO realQuizDAO = new RealQuizDAO(conn);
            FriendsDAO friendsDAO = new FriendsDAO(conn);

            if (!currentUser.equals(targetUser)) {
                List<String> myFriends = friendsDAO.getFriends(currentUser);
//                if (!myFriends.contains(targetUser)) {
//                    response.sendRedirect("accessDenied.jsp");
//                    return;
//                }
            }

            List<QuizResult> quizHistory = quizHistoryDAO.getUserHistory(targetUser);

            request.setAttribute("quizHistory", quizHistory);
            request.setAttribute("realQuizDAO", realQuizDAO);
            request.setAttribute("targetUser", targetUser);
            request.getRequestDispatcher("quizHistory.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to load quiz history", e);
        }

    }
}
