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
  <!-- Header with welcome banner -->
  <div style="position: relative; margin-bottom: 20px;">
    <div style="text-align: center; margin-top: 10px;">
      <a href="MyProfileServlet" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
        <img src="assets/profile.webp" alt="Profile" style="width: 22px; height: 22px;" />
        My Profile
      </a>
    </div>
  </div>

  <!-- Navigation bar with icons -->
  <div class="nav-links mb-20" style="display: flex; justify-content: center; gap: 20px;">
    <a href="Homepage?t=<%= System.currentTimeMillis() %>" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
      <img src="assets/backtohomepage.webp" alt="Home" style="width: 20px; height: 20px;" />
      Back to Admin Homepage
    </a>

    <a href="AdminAnnouncementServlet" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
      <img src="assets/annoucments.webp" alt="Announcements" style="width: 20px; height: 20px;" />
      Announcements
    </a>

    <a href="AdminUserServlet" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
      <img src="assets/friendrequests.webp" alt="Users" style="width: 20px; height: 20px;" />
      Users
    </a>
  </div>

  <!-- Error message -->
  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="error-message">
    <p><%= error %></p>
  </div>
  <% } %>

  <!-- Site Statistics -->
  <div class="recent-messages mt-20">
    <h3 style="display: flex; align-items: center; gap: 8px;">
      <img src="assets/stats.webp" alt="Stats" style="width: 24px; height: 24px;" />
      Site Statistics
    </h3>
    <ul class="message-list">
      <li class="card message-card">
        <p>Total Users:
          <strong><%= request.getAttribute("totalUsers") %></strong>
        </p>
        <p>Total Quizzes:
          <strong><%= request.getAttribute("totalQuizzes") %></strong>
        </p>
      </li>
    </ul>
  </div>

  <!-- Bottom bar with icon -->
  <div class="bottom-bar mt-30" style="text-align: center; margin-top: 40px;">
    <a href="Homepage" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
      <img src="assets/backtohomepage.webp" alt="Home" style="width: 20px; height: 20px; vertical-align: middle;" />
      Home
    </a>
  </div>
</div>
</body>
</html>
