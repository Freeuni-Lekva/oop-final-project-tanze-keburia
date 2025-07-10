package servlets.quiz_participation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/previousQuestion")
public class PreviousQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");

        if(currentIndex != null && currentIndex > 0) {
            session.setAttribute("currentIndex", currentIndex - 1);
        }

        response.sendRedirect("questionPage.jsp");
    }
}
