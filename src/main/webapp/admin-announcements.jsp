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
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Welcome, Admin <%= session.getAttribute("username") %></h2>
        <a href="AdminDashboardServlet" class="link link-blue">Dashboard</a>
    </div>

    <h2>Create New Announcement</h2>
    <a href="adminHomepage.jsp" class="link link-blue">Back to Admin Homepage</a>
    <form method="post" action="AdminAnnouncementServlet">
        <input type="hidden" name="action" value="create">
        <textarea class="input-full" name="body" required placeholder="Enter announcement text..."></textarea><br>
        <button type="submit" class="btn btn-green">Create Announcement</button>
    </form>

    <h2>All Announcements</h2>
    <%
        List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (announcements != null && !announcements.isEmpty()) {
            for (Announcement announcement : announcements) {
    %>
    <div class="card announcement-card">
        <p><strong>By <%= announcement.getAuthor() %></strong> on <%= sdf.format(announcement.getPublishDate()) %></p>
        <p><%= announcement.getBody() %></p>

        <form method="post" action="AdminAnnouncementServlet" onsubmit="return confirm('Are you sure?');">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="announcementId" value="<%= announcement.getId() %>">
            <button type="submit" class="btn btn-red">Delete</button>
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p>No announcements found.</p>
    <% } %>
</div>
</body>
</html>