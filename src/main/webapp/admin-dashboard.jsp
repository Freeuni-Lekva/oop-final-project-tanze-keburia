<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h2>Admin Dashboard</h2>
    <a href="MyProfileServlet" class="link link-blue">My Profile</a>
  </div>

  <div class="nav-links mb-20">
    <a href="Homepage?t=<%= System.currentTimeMillis() %>" class="link link-blue">Back to Admin Homepage</a> |
    <a href="AdminAnnouncementServlet" class="link link-blue">Announcements</a> |
    <a href="AdminUserServlet" class="link link-blue">Users</a>
  </div>

  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="error-message">
    <p><%= error %></p>
  </div>
  <% } %>

  <div class="recent-messages mt-20">
    <h3>Site Statistics</h3>
    <ul class="message-list">
      <li class="card message-card">
        <p>Total Users: <strong><%= request.getAttribute("totalUsers") %></strong></p>
        <p>Total Quizzes: <strong><%= request.getAttribute("totalQuizzes") %></strong></p>
      </li>
    </ul>
  </div>

  <div class="bottom-bar mt-30">
    <a href="Homepage" class="link link-blue">Home</a>
  </div>
</div>
</body>

</html>