<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session != null) {
        session.invalidate();
    }
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<html>
<head>
    <title>Logged Out</title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css" />
</head>
<body>
<div class="login-box">
        <h2>You have been logged out.</h2>
        <a href="login.jsp">Click here to log in again</a>
</div>
</body>
</html>