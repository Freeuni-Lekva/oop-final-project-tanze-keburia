<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Manage Users</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h1>Manage Users</h1>
    <a href="AdminDashboardServlet" class="link link-blue">Dashboard</a>
  </div>

  <p>Welcome, <strong><%= request.getAttribute("currentUser") %></strong></p>

  <% String success = (String) session.getAttribute("statusMessage");
    if (success != null) {
      session.removeAttribute("statusMessage"); %>
  <div class="success-message"><%= success %></div>
  <% } %>

  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="error-message"><%= error %></div>
  <% } %>

  <div class="quizzes-container">
    <h2>All Users</h2>
    <%
      List<String> allUsers = (List<String>) request.getAttribute("allUsers");
      List<String> admins = (List<String>) request.getAttribute("admins");
      String currentUser = (String) request.getAttribute("currentUser");

      if (allUsers != null && !allUsers.isEmpty()) {
    %>
    <div class="quiz-list">
      <% for (String user : allUsers) {
        boolean isAdmin = admins != null && admins.contains(user);
      %>
      <div class="quiz-card">
        <div class="quiz-header">
          <h3><%= user %></h3>
          <% if (isAdmin) { %>
          <p class="quiz-author">(Admin)</p>
          <% } %>
        </div>
        <div class="quiz-actions">
          <% if (!user.equals(currentUser)) { %>
          <form method="post" action="AdminUserServlet">
            <input type="hidden" name="action" value="removeUser">
            <input type="hidden" name="userToRemove" value="<%= user %>">
            <button type="submit" class="btn btn-red">Remove User</button>
          </form>
          <% } %>

          <% if (!isAdmin && !user.equals(currentUser)) { %>
          <form method="post" action="AdminUserServlet">
            <input type="hidden" name="action" value="promoteUser">
            <input type="hidden" name="userToPromote" value="<%= user %>">
            <button type="submit" class="btn btn-green">Promote to Admin</button>
          </form>
          <% } else if (isAdmin && !user.equals(currentUser)) { %>
          <form method="post" action="AdminUserServlet">
            <input type="hidden" name="action" value="demoteUser">
            <input type="hidden" name="userToDemote" value="<%= user %>">
            <button type="submit" class="btn btn-purple">Remove Admin Rights</button>
          </form>
          <% } %>
        </div>
      </div>
      <% } %>
    </div>
    <% } else { %>
    <div class="no-quizzes">
      <p>No users found</p>
    </div>
    <% } %>
  </div>
</div>
</body>
</html>