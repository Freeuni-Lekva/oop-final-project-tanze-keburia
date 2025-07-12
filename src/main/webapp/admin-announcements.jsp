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

    <!-- Header -->
    <div class="header-row mb-20" style="display: flex; justify-content: space-between; align-items: center;">
        <h2>Welcome, Admin <%= session.getAttribute("username") %></h2>
        <a href="AdminDashboardServlet" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
            <img src="assets/dashboard.webp" alt="Dashboard" style="width: 20px; height: 20px;" />
            Dashboard
        </a>
    </div>

    <!-- Back link -->
    <div class="mb-20" style="text-align: center;">
        <a href="Homepage?t=<%= System.currentTimeMillis() %>" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px;">
            <img src="assets/backtohomepage.webp" alt="Home" style="width: 20px; height: 20px; vertical-align: middle;" />
            Back to Admin Homepage
        </a>
    </div>

    <!-- Create Announcement -->
    <div class="mb-30">
        <h3 style="display: flex; align-items: center; gap: 8px;">
            <img src="assets/createann.webp" alt="New Announcement" style="width: 24px; height: 24px;" />
            Create New Announcement
        </h3>
        <form method="post" action="AdminAnnouncementServlet">
            <input type="hidden" name="action" value="create">
            <textarea class="input-full mt-10" name="body" required placeholder="Enter announcement text..."></textarea><br>
            <button type="submit" class="btn btn-green mt-10">Create Announcement</button>
        </form>
    </div>

    <!-- Existing Announcements -->
    <div>
        <h3 style="display: flex; align-items: center; gap: 8px;">
            <img src="assets/annoucments.webp" alt="Announcements" style="width: 24px; height: 24px;" />
            All Announcements
        </h3>
        <%
            List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (announcements != null && !announcements.isEmpty()) {
                for (Announcement announcement : announcements) {
        %>
        <div class="card announcement-card mt-10">
            <p><strong>By <%= announcement.getAuthor() %></strong> on <%= sdf.format(announcement.getPublishDate()) %></p>
            <p><%= announcement.getBody() %></p>
            <form method="post" action="AdminAnnouncementServlet" onsubmit="return confirm('Are you sure?');">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="announcementId" value="<%= announcement.getId() %>">
                <button type="submit" class="btn btn-red mt-10">Delete</button>
            </form>
        </div>
        <%
            }
        } else {
        %>
        <p class="mt-10">No announcements found.</p>
        <% } %>
    </div>

</div>
</body>
</html>
