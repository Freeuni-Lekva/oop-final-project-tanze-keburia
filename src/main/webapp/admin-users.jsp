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

  <% String success = (String) session.getAttribute("statusMessage");
    if (success != null) {
      session.removeAttribute("statusMessage"); %>
  <div class="success-message"><%= success %></div>
  <% } %>

  <% String error = (String) request.getAttribute("error");
    if (error != null) { %>
  <div class="error-message"><%= error %></div>
  <% } %>

  <div class="recent-messages mt-20">
    <h3>All Users</h3>

    <%
      List<String> allUsers = (List<String>) request.getAttribute("allUsers");
      List<String> admins = (List<String>) request.getAttribute("admins");
      String currentUser = (String) request.getAttribute("currentUser");

      if (allUsers != null && !allUsers.isEmpty()) {
    %>
    <ul class="message-list">
      <% for (String user : allUsers) {
        boolean isAdmin = admins != null && admins.contains(user);
      %>
      <li class="card message-card">
        <div>
          <strong><a href="ProfileServlet?username=<%= user %>" class="link link-blue"><%= user %></a></strong>
          <% if (isAdmin) { %>
          <span class="tag tag-purple">(Admin)</span>
          <% } %>
        </div>

        <% if (!user.equals(currentUser)) { %>
        <div style="margin-top: 10px;">
          <form method="post" action="AdminUserServlet" style="display:inline;">
            <input type="hidden" name="action" value="removeUser">
            <input type="hidden" name="userToRemove" value="<%= user %>">
            <button type="submit" class="btn btn-red">Remove User</button>
          </form>

          <% if (!isAdmin) { %>
          <form method="post" action="AdminUserServlet" style="display:inline; margin-left:10px;">
            <input type="hidden" name="action" value="promoteUser">
            <input type="hidden" name="userToPromote" value="<%= user %>">
            <button type="submit" class="btn btn-green">Promote to Admin</button>
          </form>
          <% } else { %>
          <form method="post" action="AdminUserServlet" style="display:inline; margin-left:10px;">
            <input type="hidden" name="action" value="demoteUser">
            <input type="hidden" name="userToDemote" value="<%= user %>">
            <button type="submit" class="btn btn-purple">Remove Admin Rights</button>
          </form>
          <% } %>
        </div>
        <% } %>
      </li>
      <% } %>
    </ul>
    <% } else { %>
    <p>No users found</p>
    <% } %>
  </div>

  <div class="bottom-bar mt-30">
    <a href="Homepage" class="link link-blue">Home</a>
  </div>
</div>
</body>
</html>