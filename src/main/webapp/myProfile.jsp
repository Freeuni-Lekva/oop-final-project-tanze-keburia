<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: GUGA--%>
<%--  Date: 6/18/2025--%>
<%--  Time: 10:54 PM--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%@ page import="database.FriendsDAO" %>
<%@ page import="database.FriendRequestDAO" %>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="java.util.List" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ServletContext context = application;
    FriendsDAO friendsDAO = (FriendsDAO) context.getAttribute("friends");
    FriendRequestDAO requestDAO = (FriendRequestDAO) context.getAttribute("friendRequests");

    List<String> myFriends = friendsDAO.getFriends(username);
    List<String> requests = requestDAO.getRequestList(username);
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
</head>
<body>

<h2>Welcome, <%= username %>!</h2>

<h3>My Friends</h3>
<% if (myFriends.isEmpty()) { %>
<p>You have no friends.</p>
<% } else { %>
<ul>
    <% for (String friend : myFriends) { %>
    <li><a href="profile.jsp?username=<%= friend %>"><%= friend %></a></li>
    <% } %>
</ul>
<% } %>

<h3>Friend Requests</h3>
<% if (requests.isEmpty()) { %>
<p>No pending requests.</p>
<% } else { %>
<ul>
    <% for (String sender : requests) { %>
    <li>
        <%= sender %>
        <form action="FriendRequestResponse" method="post" style="display:inline;">
            <input type="hidden" name="sender" value="<%= sender %>" />
            <input type="hidden" name="receiver" value="<%= username %>" />
            <button type="submit" name="status" value="accept">Accept</button>
            <button type="submit" name="status" value="reject">Reject</button>
        </form>
    </li>
    <% } %>
</ul>
<% } %>


<p><a href="QuizHistoryServlet">View My Quiz History</a></p>


<p><a href="homepage.jsp">Back to Home</a></p>

</body>
</html>



