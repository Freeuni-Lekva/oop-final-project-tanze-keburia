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
    <p>Welcome, <strong><%= request.getAttribute("adminUsername") %></strong></p>
  </div>

  <div class="nav-links mb-20">
    <a href="Homepage?t=<%= System.currentTimeMillis() %>" class="link link-blue">Back to Admin Homepage</a> |
    <a href="AdminAnnouncementServlet" class="link link-blue">Announcements</a> |
    <a href="AdminUserServlet" class="link link-blue">Users</a> |
    <a href="AdminQuizzesServlet" class="link link-blue">Quizzes</a>
  </div>

  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="error-message">
    <p><%= error %></p>
  </div>
  <% } %>

  <div class="card">
    <h3>Site Statistics</h3>
    <p>Total Users: <strong><%= request.getAttribute("totalUsers") %></strong></p>
    <p>Total Quizzes: <strong><%= request.getAttribute("totalQuizzes") %></strong></p>
  </div>

  <div class="mt-20">
    <h3>Recent Announcements</h3>
    <%
      List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      if (announcements != null && !announcements.isEmpty()) {
        for (Announcement a : announcements) {
    %>
    <div class="card announcement-card">
      <p><strong><%= a.getAuthor() %></strong>: <%= a.getBody() %></p>
      <div class="timestamp"><%= sdf.format(a.getPublishDate()) %></div>
    </div>
    <%
      }
    } else {
    %>
    <div class="card">
      <p>No announcements found</p>
    </div>
    <% } %>
  </div>
</div>
</body>
</html>