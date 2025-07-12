<%@ page import="javax.servlet.http.HttpSession" %>
<%
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  String searchedUsername = (String) request.getAttribute("searchedUsername"); // optional, pass from servlet
%>
<!DOCTYPE html>
<html>
<head>
  <title>Searching...</title>
  <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="search-container">
  <div class="search-header">
    <img src="assets/searchicon.webp" alt="Search Icon" class="search-icon">
    SEARCHING...
  </div>

  <div class="result-row">
    <img src="assets/notfound.webp" alt="Not Found" class="tick-icon">
    User not found<% if (searchedUsername != null) { %>: <strong><%= searchedUsername %></strong><% } %>
  </div>

  <a href="Homepage" class="back-link">
    <img src="assets/backtohomepage.webp" alt="Back" class="back-icon">
    Back to Homepage
  </a>
</div>
</body>
</html>
