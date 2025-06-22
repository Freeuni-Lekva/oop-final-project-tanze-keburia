<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 11:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="database.FriendsDAO" %>
<%@ page import="database.FriendRequestDAO" %>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="java.util.List" %>
<%
    String currentUser = (String) session.getAttribute("username");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String profileUser = request.getParameter("username");
    if (profileUser == null || profileUser.isEmpty()) {
        response.sendRedirect("homepage.jsp");
        return;
    }

    ServletContext context = application;
    FriendsDAO friendsDAO = (FriendsDAO) context.getAttribute("friends");
    FriendRequestDAO requestDAO = (FriendRequestDAO) context.getAttribute("friendRequests");

    List<String> profileFriends = friendsDAO.getFriends(profileUser);
    List<String> myFriends = friendsDAO.getFriends(currentUser);
    List<String> pendingRequests = requestDAO.getRequestList(profileUser);

    boolean isFriend = myFriends.contains(profileUser);
    boolean requestAlreadySent = pendingRequests.contains(currentUser);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= profileUser %>'s Profile</title>
</head>
<body>

<h2><%= profileUser %>'s Profile</h2>

<% if (isFriend) { %>
<form method="post" action="RemoveFriendServlet">
    <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
    <input type="submit" value="Remove Friend" />
</form>
<% } else if (requestAlreadySent) { %>
<form method="post" action="FriendRequestResponse">
    <input type="hidden" name="receiver" value="<%= profileUser %>" />
    <input type="hidden" name="sender" value="<%= currentUser %>" />
    <input type="hidden" name="status" value="cancel" />
    <input type="submit" value="Cancel Request" />
</form>
<% } else { %>
<form method="post" action="AddServlet">
    <input type="hidden" name="receiverUsername" value="<%= profileUser %>" />
    <input type="submit" value="Add Friend" />
</form>
<% } %>

<h3><%= profileUser %>'s Friends</h3>
<% if (profileFriends.isEmpty()) { %>
<p>No friends yet.</p>
<% } else { %>
<ul>
    <% for (String friend : profileFriends) { %>
    <li><a href="profile.jsp?username=<%= friend %>"><%= friend %></a></li>
    <% } %>
</ul>
<% } %>

<p><a href="homepage.jsp">Back to Home</a></p>

</body>
</html>
