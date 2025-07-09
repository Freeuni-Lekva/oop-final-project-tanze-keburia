<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.Announcement" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Announcements</title>
</head>
<body>
<h1>Manage Announcements</h1>

<p>Welcome, <%= request.getAttribute("adminUsername") %></p>
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

<h2>Create New Announcement</h2>
<form method="post" action="admin-announcements">
    <input type="hidden" name="action" value="create">
    <label for="body">Announcement Text:</label>
    <textarea name="body" id="body" placeholder="Enter announcement text..." required></textarea>
    <button type="submit">Create Announcement</button>
</form>

<h2>All Announcements</h2>
<%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
    if (announcements != null && !announcements.isEmpty()) {
        for (Announcement announcement : announcements) {
%>
<div>
    <p>By <%= announcement.getAuthor() %> on <%= announcement.getPublishDate() %></p>
    <p><%= announcement.getBody() %></p>
    <form method="post" action="admin-announcements" style="display: inline;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="announcementId" value="<%= announcement.getId() %>">
        <button type="submit" onclick="return confirm('Are you sure you want to delete this announcement?')">
            Delete
        </button>
    </form>
    <hr>
</div>
<%
    }
} else {
%>
<p>No announcements found.</p>
<% } %>
</body>
</html>