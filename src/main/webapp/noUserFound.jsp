<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/19/2025
  Time: 8:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
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
  <title>User Not Found</title>
</head>
<body>
<h2>No user with that name exists.</h2>
<p><a href="homepage.jsp">Back to Home</a></p>
</body>
</html>
