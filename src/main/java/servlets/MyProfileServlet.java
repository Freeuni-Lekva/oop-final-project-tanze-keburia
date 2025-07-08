package servlets;

import database.DatabaseConnector;
import database.FriendsDAO;
import database.FriendRequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/MyProfileServlet")
public class MyProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            FriendRequestDAO requestDAO = new FriendRequestDAO(conn, friendsDAO);

            List<String> myFriends = friendsDAO.getFriends(username);
            List<String> requests = requestDAO.getRequestList(username);

            request.setAttribute("myFriends", myFriends);
            request.setAttribute("requests", requests);

            request.getRequestDispatcher("myProfile.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to load profile data", e);
        }
    }
}
