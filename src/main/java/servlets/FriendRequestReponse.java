package servlets;

import database.FriendRequestDAO;
import database.FriendsDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/FriendRequestResponse")
public class FriendRequestReponse extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");
        String status =  request.getParameter("status");
        ServletContext context = getServletContext();
        FriendsDAO friendsDAO = (FriendsDAO)context.getAttribute("friends");
        FriendRequestDAO friendRequestDAO = (FriendRequestDAO)context.getAttribute("friendRequests");
        friendRequestDAO.removeRequest(sender, receiver);
        if(status.equals("accept")){
            friendsDAO.addFriends(sender, receiver);
        }
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
