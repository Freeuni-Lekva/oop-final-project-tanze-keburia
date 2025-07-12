<%@ page import="classes.User" %>
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
    <title>Searching...</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="profile-container">
    <div class="search-header">
        <img src="assets/searchicon.webp" alt="Search Icon" class="search-icon">
        SEARCHING...
    </div>

    <div class="result-row">
        <img src="assets/greentick.webp" alt="Found" class="tick-icon">
        User found:
        <% if (foundUser.equals(username)) { %>
        <a href="MyProfileServlet"><%= foundUser %></a>
        <% } else { %>
        <a href="ProfileServlet?username=<%= foundUser %>"><%= foundUser %></a>
        <% } %>
    </div>

    <!-- Move this inside the white card -->
    <a href="Homepage" class="back-link">
        <img src="assets/backtohomepage.webp" alt="Back" class="back-icon">
        Back to Homepage
    </a>
</div>
</body>

</html>
