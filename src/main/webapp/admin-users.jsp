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
<%--<a href="logout">Logout</a>--%>

<p>
  <a href="AdminDashboardServlet">Dashboard</a> |
<%--  <a href="AdminUserServlet">Users</a>--%>
</p>

<%
  String success = (String) request.getAttribute("success");
  if (success != null) { %>
<p>Success: <%= success %></p>
<% } %>

<h2>Remove User</h2>
<form method="post" action="AdminUserServlet">
  <input type="hidden" name="action" value="removeUser">
  Username: <input type="text" name="userToRemove" required>
  <button type="submit">Remove</button>
</form>

<h2>Promote to Admin</h2>
<form method="post" action="AdminUserServlet">
  <input type="hidden" name="action" value="promoteUser">
  Username: <input type="text" name="userToPromote" required>
  <button type="submit">Promote</button>
</form>

<h2>Admins</h2>
<%
  List<String> admins = (List<String>) request.getAttribute("admins");
  String currentUser = (String) request.getAttribute("currentUser");
  if (admins != null) {
    for (String admin : admins) {
%>
<p>
    <%= admin %>
    <% if (!admin.equals(currentUser)) { %>
<form method="post" action="AdminUserServlet" style="display:inline">
  <input type="hidden" name="action" value="demoteUser">
  <input type="hidden" name="userToDemote" value="<%= admin %>">
  <button type="submit">Remove Admin</button>
</form>
<% } %>
</p>
<%
    }
  }
%>
</body>
</html>