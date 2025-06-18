<%@ page import="classes.User" %><%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    User resultUser = (User) request.getAttribute("resultUser");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Result</title>
</head>
<body>

<h2>Search Result</h2>

<% if (resultUser != null) { %>
<p>User found: <strong><%= resultUser.getUserName() %></strong></p>
<a href="profile.jsp?username=<%= resultUser.getUserName() %>">View Profile</a>
<% } else { %>
<p> No user with that name exists.</p>
<% } %>

<p><a href="homepage.jsp">Back to Home</a></p>

</body>
</html>
