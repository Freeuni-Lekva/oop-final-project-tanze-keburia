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

<a href="AdminDashboardServlet">Dashboard</a>

<%
  String success = (String) session.getAttribute("statusMessage");
  if (success != null) {
    session.removeAttribute("statusMessage");
%>
<p><%= success %></p>
<% } %>

<% String error = (String) request.getAttribute("error");
  if (error != null) { %>
<p><%= error %></p>
<% } %>

<h2>All Users</h2>
<%
  List<String> allUsers = (List<String>) request.getAttribute("allUsers");
  List<String> admins = (List<String>) request.getAttribute("admins");
  String currentUser = (String) request.getAttribute("currentUser");

  if (allUsers != null && !allUsers.isEmpty()) {
%>
<table>
  <% for (String user : allUsers) {
    boolean isAdmin = admins != null && admins.contains(user);
  %>
  <tr>
    <td><%= user %></td>
    <td>
      <% if (!user.equals(currentUser)) { %>
      <form method="post" action="AdminUserServlet">
        <input type="hidden" name="action" value="removeUser">
        <input type="hidden" name="userToRemove" value="<%= user %>">
        <button type="submit">Remove User</button>
      </form>
      <% } %>
    </td>
    <td>
      <% if (!isAdmin && !user.equals(currentUser)) { %>
      <form method="post" action="AdminUserServlet">
        <input type="hidden" name="action" value="promoteUser">
        <input type="hidden" name="userToPromote" value="<%= user %>">
        <button type="submit">Promote to Admin</button>
      </form>
      <% } else if (isAdmin && !user.equals(currentUser)) { %>
      <form method="post" action="AdminUserServlet">
        <input type="hidden" name="action" value="demoteUser">
        <input type="hidden" name="userToDemote" value="<%= user %>">
        <button type="submit">Remove Admin Rights</button>
      </form>
      <% } %>
    </td>
  </tr>
  <% } %>
</table>
<% } else { %>
<p>No users found</p>
<% } %>
</body>
</html>