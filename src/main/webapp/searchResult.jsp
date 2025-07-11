<%@ page import="classes.User" %><%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String foundUser = (String) request.getAttribute("foundUser");
    boolean userFound = (foundUser != null && !foundUser.isEmpty());

    if (!userFound) {
        response.sendRedirect("noUserFound.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Result</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard" style="justify-content: flex-start;">
    <h2>Search Result</h2>
    <p>User found:
        <% if (foundUser.equals(username)) { %>
        <a href="MyProfileServlet" class="link-blue"><%= foundUser %></a>
        <% } else { %>
        <a href="ProfileServlet?username=<%= foundUser %>" class="link-blue"><%= foundUser %></a>
        <% } %>
    </p>
    <p><a href="Homepage" class="link-blue">Back to Home</a></p>
</div>
</body>
</html>
