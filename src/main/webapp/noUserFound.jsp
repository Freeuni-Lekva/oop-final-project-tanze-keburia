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
  <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<div class="dashboard">
  <h2>User Not Found</h2>
  <p>No user with that name exists!</p>
  <p><a href="homepage.jsp" class="link-blue">Back to Home</a></p>
</div>
</html>
