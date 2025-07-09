<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.Announcement" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Admin Dashboard</h1>

<p>Welcome, <%= request.getAttribute("adminUsername") %></p>
<a href="logout">Logout</a>

<nav>
    <a href="admin-dashboard">Dashboard</a>
    <a href="admin-announcements">Manage Announcements</a>
    <a href="admin-users">Manage Users</a>
    <a href="admin-quizzes">Manage Quizzes</a>
</nav>

<h2>Site Statistics</h2>
<div>
    <h3>Total Users</h3>
    <p><%= request.getAttribute("totalUsers") %></p>
</div>
<div>
    <h3>Total Quizzes</h3>
    <p><%= request.getAttribute("totalQuizzes") %></p>
</div>
<div>
    <h3>Active Announcements</h3>
    <p>
        <%
            List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
            if (announcements != null) {
                System.out.println(announcements.size());
            } else {
                System.out.println(0);
            }
        %>
    </p>
</div>

<h2>Recent Announcements</h2>
<%
    List<Announcement> recentAnnouncements = (List<Announcement>) request.getAttribute("announcements");
    if (recentAnnouncements != null && !recentAnnouncements.isEmpty()) {
        int count = 0;
        for (Announcement announcement : recentAnnouncements) {
            if (count >= 5) break; // Show only first 5
%>
<div>
    <p>By <%= announcement.getAuthor() %> on <%= announcement.getPublishDate() %></p>
    <p><%= announcement.getBody() %></p>
    <hr>
</div>
<%
        count++;
    }
} else {
%>
<p>No announcements found.</p>
<% } %>
</body>
</html>