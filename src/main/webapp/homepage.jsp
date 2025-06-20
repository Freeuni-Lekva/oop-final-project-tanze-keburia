<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    String username;
    if (session != null) {
        username = (String) session.getAttribute("username");
    } else {
        username = null;
    }

    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>

<h2>Welcome, <%= username %>!</h2>

<h3>Search for a User</h3>
<form method="get" action="SearchServlet">
    <input type="text" name="username" placeholder="Enter username" required />
    <input type="submit" value="Search" />
</form>

<a href="myProfile.jsp">My Profile</a> |
<a href="logout.jsp">Log out</a>

</body>
</html>

