package servlets.challenges;

import classes.social.Challenge;
import database.database_connection.DatabaseConnector;
import database.social.ChallengeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeleteChallenge")
public class DeleteChallengeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("login.jsp");
        }
        String receiver = session.getAttribute("username").toString();
        String sender = request.getParameter("sender");
        String quizName = request.getParameter("quiz_name");
        double score =   Double.parseDouble(request.getParameter("score"));
        String quizID = request.getParameter("quiz_id");


        Challenge challenge = new Challenge(sender, receiver, quizID, quizName, score);
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            ChallengeDAO challengeDAO = new ChallengeDAO(conn);
            challengeDAO.removeChallenge(challenge);
        }catch(SQLException e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
      //  request.getRequestDispatcher("ViewChallenges?username="+receiver).forward(request, response);
        response.sendRedirect("ViewChallenges?username=" + receiver);

    }
}
