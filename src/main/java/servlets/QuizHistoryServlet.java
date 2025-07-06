package servlets;
import classes.QuizResult;
import database.MockQuizHistoryDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/QuizHistoryServlet")
public class QuizHistoryServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        ServletContext context = getServletContext();
        MockQuizHistoryDAO quizDAO = (MockQuizHistoryDAO) context.getAttribute("quizzes");
        List<QuizResult> quizzes = quizDAO.getUserHistory(username);
        request.setAttribute("History", quizzes);
        request.getRequestDispatcher("quizHistory.jsp").forward(request, response);
    }
}