package servlets;

import database.FriendsDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String receiverId = request.getParameter("receiverUsername");
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String senderId = session.getAttribute("username").toString();
        FriendsDAO friendsDAO = (FriendsDAO)servletContext.getAttribute("friends");
        friendsDAO.removeFriends(senderId, receiverId);
        response.sendRedirect("profile.jsp?username=" + receiverId);
    }
}
