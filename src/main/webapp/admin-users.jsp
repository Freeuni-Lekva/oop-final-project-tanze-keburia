<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Manage Users</title>
</head>
<body>
<h1>Manage Users</h1>

<p>Welcome, <%= request.getAttribute("currentUser") %></p>
<a href="logout">Logout</a>

<nav>
  <a href="admin-dashboard">Dashboard</a>
  <a href="admin-announcements">Manage Announcements</a>
  <a href="admin-users">Manage Users</a>
  <a href="admin-quizzes">Manage Quizzes</a>
</nav>

<%
  String success = (String) request.getAttribute("success");
  String error = (String) request.getAttribute("error");
  if (success != null) {
%>
<div>Success: <%= success %></div>
<% } %>
<% if (error != null) { %>
<div>Error: <%= error %></div>
<% } %>

<h2>Remove User Account</h2>
<p>Remove a user account permanently from the system.</p>
<form method="post" action="admin-users">
  <input type="hidden" name="action" value="removeUser">
  <label for="userToRemove">Username to Remove:</label>
  <input type="text" name="userToRemove" id="userToRemove" placeholder="Enter username" required>
  <button type="submit" onclick="return confirm('Are you sure you want to remove this user? This action cannot be undone.')">
    Remove User
  </button>
</form>

<h2>Promote User to Admin</h2>
<p>Grant administrator privileges to a user account.</p>
<form method="post" action="admin-users">
  <input type="hidden" name="action" value="promoteUser">
  <label for="userToPromote">Username to Promote:</label>
  <input type="text" name="userToPromote" id="userToPromote" placeholder="Enter username" required>
  <button type="submit">Promote to Admin</button>
</form>

<h2>Current Administrators</h2>
<p>List of all users with administrator privileges.</p>
<%
  List<String> admins = (List<String>) request.getAttribute("admins");
  String currentUser = (String) request.getAttribute("currentUser");
  if (admins != null && !admins.isEmpty()) {
    for (String admin : admins) {
      boolean isCurrentUser = admin.equals(currentUser);
%>
<div>
  <strong><%= admin %></strong>
  <% if (isCurrentUser) { %>
  <span> (You)</span>
  <% } %>

  <% if (!isCurrentUser) { %>
  <form method="post" action="admin-users" style="display: inline;">
    <input type="hidden" name="action" value="demoteUser">
    <input type="hidden" name="userToDemote" value="<%= admin %>">
    <button type="submit" onclick="return confirm('Are you sure you want to remove admin privileges from <%= admin %>?')">
      Remove Admin
    </button>
  </form>
  <% } else { %>
  <span> - Cannot remove own admin rights</span>
  <% } %>
</div>
<%
  }
} else {
%>
<p>No administrators found.</p>
<% } %>
</body>
</html>