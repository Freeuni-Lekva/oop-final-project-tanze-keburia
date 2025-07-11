<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 10-Jul-25
  Time: 6:27 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setHeader("Expires", "0");

  if (session.getAttribute("username") == null) {
    response.sendRedirect("login.jsp");
    return;
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

  <div class="recent-messages mt-20">
    <h3>Announcements</h3>

    <%
      List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
      if (announcements != null && !announcements.isEmpty()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    %>
    <ul class="message-list">
      <% for (Announcement announcement : announcements) { %>
      <li class="card message-card">
        <p><%= announcement.getBody() %></p>
        <div class="timestamp">
          Posted by <%= announcement.getAuthor() %> on <%= sdf.format(announcement.getPublishDate()) %>
        </div>
      </li>
      <% } %>
    </ul>
    <% } else { %>
    <p>No announcements found.</p>
    <% } %>
  </div>

  <div class="bottom-bar mt-30">
    <a href="Homepage" class="link link-blue">Home</a>
  </div>
</div>
</body>

</html>