package servlets.navigation;

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
import java.util.List;

@WebServlet("/ViewChallenges")
public class ViewChallengesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);

        if(session == null) {
            response.sendRedirect("login.jsp");
        }
        String username = session.getAttribute("username").toString();
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            ChallengeDAO challenges = new ChallengeDAO(conn);
            if(challenges == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            List<Challenge> challengeList = challenges.getUserChallenges(username);
            request.setAttribute("challengeList", challengeList);
            request.getRequestDispatcher("viewChallenges.jsp").forward(request, response);
        }catch(SQLException e){
            response.sendError(500);
        }
    }
}
