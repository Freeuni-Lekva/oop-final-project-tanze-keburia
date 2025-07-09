<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 07-Jul-25
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Mail" %>
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
    <title>Messages with <%= otherUser %></title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Conversation with <%= otherUser %></h2>
        <a href="Homepage" class="link link-blue">Home</a>
    </div>

    <div class="recent-messages">
        <h3>Messages</h3>
        <% if (conversation == null || conversation.isEmpty()) { %>
        <p>No messages found with <%= otherUser %>.</p>
        <% } else { %>
        <ul class="message-list">
            <% for (Mail mail : conversation) { %>
            <li class="card message-card">
                <div>
                    <strong>From:</strong> <%= mail.getSender() %><br>
                    <strong>Subject:</strong> <%= mail.getSubject() %>
                </div>
                <%
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedTime = sdf.format(mail.getTimestamp());
                %>
                <div class="timestamp"><%= formattedTime %></div>

                <a href="ViewMail?id=<%= mail.getId() %>" class="link link-blue">View Message</a>

                <% if (mail.getReceiver().equals(currentUser)) { %>
                <form action="DeleteMail" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= mail.getId() %>" />
                    <button type="submit" class="btn btn-red" style="margin-left: 10px;">Delete</button>
                </form>
                <% } %>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="mail-links mt-20 mb-20">
        <a href="InboxServlet" class="link link-blue">Back to Inbox</a>
        <a href="compose.jsp" class="link link-blue">Compose New</a>
    </div>
</div>
</body>
</html>
