package servlets;
import classes.QuizResult;
import database.QuizHistoryDAO;
import database.RealQuizDAO;
import database.FriendsDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/QuizHistoryServlet")
public class QuizHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String targetUser = request.getParameter("username");

        if (targetUser == null || targetUser.isEmpty()) {
            targetUser = currentUser;
        }

        ServletContext context = getServletContext();
        QuizHistoryDAO quizHistoryDAO = (QuizHistoryDAO) context.getAttribute("quizHistoryDAO");
        RealQuizDAO realQuizDAO = (RealQuizDAO) context.getAttribute("quizDAO");
        FriendsDAO friendsDAO = (FriendsDAO) context.getAttribute("friends");

        if (quizHistoryDAO == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "QuizHistoryDAO not initialized");
            return;
        }

        if (!currentUser.equals(targetUser)) {
            if (friendsDAO == null || !friendsDAO.getFriends(currentUser).contains(targetUser)) {
                response.sendRedirect("accessDenied.jsp");
                return;
            }
        }

        List<QuizResult> quizHistory = quizHistoryDAO.getUserHistory(targetUser);
        request.setAttribute("quizHistory", quizHistory);
        request.setAttribute("realQuizDAO", realQuizDAO);
        request.setAttribute("targetUser", targetUser);
        request.getRequestDispatcher("quizHistory.jsp").forward(request, response);
    }
}