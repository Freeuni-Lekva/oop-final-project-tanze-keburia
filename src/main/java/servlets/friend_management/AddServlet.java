package servlets.friend_management;

import database.social.FriendsDAO;
import database.database_connection.DatabaseConnector;
import database.social.FriendRequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String receiverId = request.getParameter("receiverUsername");
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        String senderId = session.getAttribute("username").toString();

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            FriendRequestDAO friendRequestDAO = new FriendRequestDAO(conn, friendsDAO);
            friendRequestDAO.createRequest(senderId, receiverId);
        } catch (SQLException e) {
            throw new ServletException("Database error while sending friend request", e);
        }

        response.sendRedirect("ProfileServlet?username=" + receiverId);
    }
}
