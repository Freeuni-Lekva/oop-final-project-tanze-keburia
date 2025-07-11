<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.mail.Mail" %>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  String idParam = request.getParameter("id");
  if (idParam == null) {
    response.sendRedirect("InboxServlet"); // fallback
    return;
  }

  Mail mail = (Mail) request.getAttribute("mail");
  if (mail == null) {
    response.getWriter().write("Message not found.");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>View Message</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h2>Message</h2>
    <a href="MyProfileServlet" class="link link-blue">My Profile</a>
  </div>

  <div class="card mb-20">
    <p><strong>From:</strong> <%= mail.getSender() %></p>
    <p><strong>To:</strong> <%= mail.getReceiver() %></p>
    <p><strong>Subject:</strong> <%= mail.getSubject() %></p>
    <p><strong>Time:</strong> <%= mail.getTimestamp() %></p>
    <hr>
    <p><%= mail.getContent().replaceAll("\n", "<br/>") %></p>
  </div>

  <div class="bottom-bar mt-30">
    <div class="nav-links">
      <a href="InboxServlet" class="link link-blue">Back to Inbox</a>
      <a href="SentServlet" class="link link-blue">Back to Sent</a>
    </div>
    <a href="Homepage" class="link link-blue">Home</a>
  </div>

</div>
</body>
</html>
