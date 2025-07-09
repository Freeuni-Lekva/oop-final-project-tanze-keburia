<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 10-Jul-25
  Time: 2:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Announcement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%
    String username = (String) request.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Announcement> allAnnouncements = (List<Announcement>) request.getAttribute("allAnnouncements");
    if (allAnnouncements != null) {
        Collections.sort(allAnnouncements, new Comparator<Announcement>() {
            @Override
            public int compare(Announcement a1, Announcement a2) {
                return a2.getPublishDate().compareTo(a1.getPublishDate());
            }
        });
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Announcements</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>All Announcements</h2>
        <a href="Homepage" class="link link-blue">Back to Homepage</a>
    </div>

    <div class="announcements-list">
        <% if (allAnnouncements == null || allAnnouncements.isEmpty()) { %>
        <div class="no-announcements">
            <p>No announcements available</p>
        </div>
        <% } else { %>
        <div class="announcements-grid">
            <% for (Announcement announcement : allAnnouncements) { %>
            <div class="card announcement-card">
                <div class="announcement-header">
                    <strong>By: <%= announcement.getAuthor() %></strong>
                    <div class="timestamp">
                        <%
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String formattedTime = sdf.format(announcement.getPublishDate());
                        %>
                        <%= formattedTime %>
                    </div>
                </div>
                <div class="announcement-body">
                    <p><%= announcement.getBody() %></p>
                </div>
            </div>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>