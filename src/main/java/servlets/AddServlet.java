package servlets;

import database.FriendRequestDAO;
import database.FriendsDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String receiverId = request.getParameter("receiverUsername");
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String senderId = session.getAttribute("username").toString();
        FriendRequestDAO friendRequestDAO = (FriendRequestDAO)servletContext.getAttribute("friendsRequestDAO");
        friendRequestDAO.addRequest(senderId, receiverId);
        response.sendRedirect("profile.jsp?username=" + receiverId);
    }
}
