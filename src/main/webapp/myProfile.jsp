<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
</head>
<body>
<h2><%= username %></h2>
<a href="homepage.jsp">Back to Home</a>
</body>
</html>
