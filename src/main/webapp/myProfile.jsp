<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<<%@ page import="database.FriendsDAO" %>
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
    List<String> myFriends = friendsDAO.getFriends(username);
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

<a href="homepage.jsp">Back to Home</a>

</body>
</html>
