<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 10-Jul-25
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      margin: 0;
      padding: 20px;
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    .nav-links {
      margin: 20px 0;
    }
    .nav-links a {
      margin-right: 15px;
      text-decoration: none;
      color: #0066cc;
    }
    .stats-container {
      background: #f5f5f5;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 20px;
    }
    .announcements {
      margin-top: 20px;
    }
    .announcement {
      padding: 10px;
      border-bottom: 1px solid #eee;
    }
    .error {
      color: red;
    }
  </style>
</head>
<body>
<div class="header">
  <h1>Admin Dashboard</h1>
  <div>Welcome, <strong><%= request.getAttribute("adminUsername") %></strong></div>
</div>

<div class="nav-links">
  <a href="adminHomepage.jsp">Back to Admin Homepage</a>
  <a href="AdminAnnouncementServlet">Announcements</a>
  <a href="AdminUserServlet">Users</a>
  <a href="AdminQuizzesServlet">Quizzes</a>
</div>

<% String error = (String) request.getAttribute("error");
  if (error != null) { %>
<div class="error"><%= error %></div>
<% } %>

<div class="stats-container">
  <h2>Site Statistics</h2>
  <p>Total Users: <strong><%= request.getAttribute("totalUsers") %></strong></p>
  <p>Total Quizzes: <strong><%= request.getAttribute("totalQuizzes") %></strong></p>
</div>

<div class="announcements">
  <h2>Recent Announcements</h2>
  <%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
    if (announcements != null && !announcements.isEmpty()) {
      for (Announcement a : announcements) {
  %>
  <div class="announcement">
    <p><strong><%= a.getAuthor() %></strong>: <%= a.getBody() %></p>
    <small><%= a.getPublishDate() %></small>
  </div>
  <%
    }
  } else {
  %>
  <p>No announcements found</p>
  <% } %>
</div>

</body>
</html>