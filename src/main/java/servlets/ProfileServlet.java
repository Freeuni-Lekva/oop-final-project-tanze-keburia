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

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentUser = (String) session.getAttribute("username");
        String profileUser = request.getParameter("username");
        if (profileUser == null || profileUser.isEmpty()) {
            response.sendRedirect("Homepage");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            FriendRequestDAO requestDAO = new FriendRequestDAO(conn, friendsDAO);

            List<String> profileFriends = friendsDAO.getFriends(profileUser);
            List<String> myFriends = friendsDAO.getFriends(currentUser);
            List<String> pendingRequests = requestDAO.getRequestList(profileUser);

            boolean isFriend = myFriends.contains(profileUser);
            boolean requestAlreadySent = pendingRequests.contains(currentUser);

            request.setAttribute("profileUser", profileUser);
            request.setAttribute("profileFriends", profileFriends);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("isFriend", isFriend);
            request.setAttribute("requestAlreadySent", requestAlreadySent);

            request.getRequestDispatcher("profile.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error loading profile", e);
        }
    }
}
