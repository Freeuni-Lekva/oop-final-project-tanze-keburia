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

@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("login.jsp");
        }
        String sender = session.getAttribute("username").toString();
        System.out.println(sender);
        String receiver = request.getParameter("receiver");
        double bestScore = Double.parseDouble(request.getParameter("score"));
        String quizID = request.getParameter("quizID");
        String quizName = request.getParameter("quizName");
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            ChallengeDAO challengeDAO = new ChallengeDAO(conn);
            Challenge challenge = new Challenge(sender, receiver, quizID, quizName, bestScore);
            challengeDAO.addChallenge(challenge);
        }catch(SQLException e){
            e.printStackTrace();
            response.getWriter().println("Error while loading database");
            response.sendError(500);
        }
        response.getWriter().println("Challenge has been sent");
    }
}
