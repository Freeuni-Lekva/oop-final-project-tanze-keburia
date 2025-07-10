package servlets;

import database.DatabaseConnector;
import database.FriendsDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String receiverId = request.getParameter("receiverUsername");
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String senderId = session.getAttribute("username").toString();
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            friendsDAO.removeFriends(senderId, receiverId);
        } catch (SQLException e) {
            throw new ServletException("Database error while removing friend", e);
        }
        response.sendRedirect("ProfileServlet?username=" + receiverId);
    }
}
