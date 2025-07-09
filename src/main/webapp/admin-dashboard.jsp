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
<h1>Welcome, <%= request.getAttribute("adminUsername") %></h1>

<a href="adminHomepage.jsp">Back to Admin Homepage</a>

<div>
    <a href="AdminAnnouncementServlet">Announcements</a> |
    <a href="AdminUserServlet">Users</a> |
    <a href="AdminQuizzesServlet">Quizzes</a>
</div>

<h2>Site Statistics</h2>
<div>
    <p>Total Users: <%= request.getAttribute("totalUsers") %></p>
    <p>Total Quizzes: <%= request.getAttribute("totalQuizzes") %></p>
</div>

<h2>Recent Announcements</h2>
<%
    List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
    if (announcements != null && !announcements.isEmpty()) {
        for (Announcement a : announcements) {
%>
<p><%= a.getAuthor() %>: <%= a.getBody() %></p>
<%
    }
} else {
%>
<p>No announcements</p>
<% } %>

</body>
</html>