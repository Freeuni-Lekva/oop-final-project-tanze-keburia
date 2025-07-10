package servlets.friend_management;

import database.database_connection.DatabaseConnector;
import database.social.FriendRequestDAO;
import database.social.FriendsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/FriendRequestResponse")
public class FriendRequestReponse extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");
        String status =  request.getParameter("status");
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            FriendRequestDAO friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);

            friendRequestDAO.removeRequest(sender, receiver);

            if ("accept".equalsIgnoreCase(status)) {
                friendsDAO.addFriends(sender, receiver);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error handling friend request response", e);
        }

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "Homepage");
    }
}