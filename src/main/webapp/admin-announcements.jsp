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
<%--<a href="logout">Logout</a>--%>

<p>
    <a href="AdminDashboardServlet">Dashboard</a> |
<%--    <a href="AdminAnnouncementServlet">Announcements</a>--%>
</p>

<%
    String success = (String) request.getAttribute("success");
    if (success != null) { %>
<p>Success: <%= success %></p>
<% } %>

<h2>Create New Announcement</h2>
<form method="post" action="AdminAnnouncementServlet">
    <input type="hidden" name="action" value="create">
    <textarea name="body" required></textarea>
    <button type="submit">Create</button>
</form>

<h2>All Announcements</h2>
<%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
    if (announcements != null) {
        for (Announcement announcement : announcements) {
%>
<div>
    <p>By <%= announcement.getAuthor() %> on <%= announcement.getPublishDate() %></p>
    <p><%= announcement.getBody() %></p>

    <form method="post" action="AdminAnnouncementServlet">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="announcementId" value="<%= announcement.getId() %>">
        <button type="submit">Delete</button>
    </form>
</div>
<hr>
<%
    }
} else {
%>
<p>No announcements found</p>
<% } %>
</body>
</html>