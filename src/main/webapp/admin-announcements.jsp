<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Announcements</title>
    <style>
        .announcement {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        textarea {
            width: 100%;
            min-height: 100px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<h1>Manage Announcements</h1>
<p>Welcome, ${adminUsername}</p>

<p><a href="AdminDashboardServlet">Dashboard</a></p>

<h2>Create New Announcement</h2>
<form method="post" action="AdminAnnouncementServlet">
    <input type="hidden" name="action" value="create">
    <textarea name="body" required placeholder="Enter announcement text..."></textarea><br>
    <button type="submit">Create Announcement</button>
</form>

<h2>All Announcements</h2>
<%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    if (announcements != null && !announcements.isEmpty()) {
        for (Announcement announcement : announcements) {
%>
<div class="announcement">
    <p><strong>By <%= announcement.getAuthor() %></strong> on <%= sdf.format(announcement.getPublishDate()) %></p>
    <p><%= announcement.getBody() %></p>

    <form method="post" action="AdminAnnouncementServlet" onsubmit="return confirm('Are you sure?');">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="announcementId" value="<%= announcement.getId() %>">
        <button type="submit">Delete</button>
    </form>
</div>
<%
    }
} else {
%>
<p>No announcements found.</p>
<% } %>
</body>
</html>