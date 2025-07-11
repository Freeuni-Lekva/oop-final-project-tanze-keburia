package servlets.challenges;

import classes.social.Challenge;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AcceptChallenge")
public class AcceptChallengeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String receiver = session.getAttribute("username").toString();
        String sender = request.getParameter("sender");
        String quizID =  request.getParameter("quiz_id");
        String quizName= request.getParameter("quiz_name");
        double score  =Double.parseDouble(request.getParameter("score"));
        session.setAttribute("challenge", new Challenge(sender,receiver, quizID, quizName, score));
        request.getRequestDispatcher("startQuiz?id="+quizID).forward(request, response);
    }
}
