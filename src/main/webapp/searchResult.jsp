<%@ page import="classes.User" %><%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
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
</head>
<body>

<h2>Search Result</h2>

<p>User found:
    <% if (foundUser.equals(username)) { %>
    <a href="myProfile.jsp"><%= foundUser %></a>
    <% } else { %>
    <a href="profile.jsp?username=<%= foundUser %>"><%= foundUser %></a>
    <% } %>
</p>

<p><a href="homepage.jsp">Back to Home</a></p>

</body>
</html>
