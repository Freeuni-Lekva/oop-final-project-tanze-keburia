<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 07-Jul-25
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.mail.Mail" %>
<%@ page import="java.util.List" %>
<%
  String currentUser = (String) request.getAttribute("currentUser");
  String otherUser = (String) request.getAttribute("otherUser");
  List<Mail> conversation = (List<Mail>) request.getAttribute("conversation");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Sent to <%= otherUser %></title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h2>Messages to <%= otherUser %></h2>
    <a href="Homepage" class="link link-blue">Home</a>
  </div>

  <div class="recent-messages">
    <h3>Found mails:</h3>
    <% if (conversation == null || conversation.isEmpty()) { %>
    <p>No sent messages found to <%= otherUser %>.</p>
    <% } else { %>
    <ul class="message-list">
      <% for (Mail mail : conversation) { %>
      <li class="card message-card">
        <div>
          <strong>To:</strong> <%= mail.getReceiver() %><br>
          <strong>Subject:</strong> <%= mail.getSubject() %>
        </div>
        <%
          java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
          String formattedTime = sdf.format(mail.getTimestamp());
        %>
        <div class="timestamp"><%= formattedTime %></div>

        <a href="ViewMail?id=<%= mail.getId() %>" class="link link-blue">View Message</a>
        <form action="DeleteSentMail" method="post" style="display: inline;">
          <input type="hidden" name="id" value="<%= mail.getId() %>" />
          <button type="submit" class="btn btn-red" style="margin-left: 10px;">Delete</button>
        </form>
      </li>
      <% } %>
    </ul>
    <% } %>
  </div>

  <div class="mail-links mt-20 mb-20">
    <a href="SentServlet" class="link link-blue">Back to Sent</a>
    <a href="compose.jsp" class="link link-blue">Compose New</a>
  </div>
</div>
</body>
</html>
