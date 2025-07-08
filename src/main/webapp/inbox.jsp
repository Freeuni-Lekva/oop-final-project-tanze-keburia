<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.Mail" %>
<%@ page import="database.MailDAO" %>
<%@ page import="java.util.List" %>
<%
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Mail> inbox = (List<Mail>) request.getAttribute("inbox");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Inbox</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>

<h2>Inbox</h2>
<% if (inbox == null || inbox.isEmpty())  { %>
<p>No messages.</p>
<% } else { %>
<ul>
  <% for (Mail mail : inbox) { %>
  <li>
    <strong>From:</strong> <%= mail.getSender() %><br/>
    <strong>Subject:</strong> <%= mail.getSubject() %><br/>
    <strong>Time:</strong> <%= mail.getTimestamp() %><br/>
    <a href="message.jsp?id=<%= mail.getId() %>">View Message</a><br/>
    <form action="DeleteMail" method="post" style="display:inline;">
      <input type="hidden" name="id" value="<%= mail.getId() %>" />
      <button type="submit">Delete</button>
    </form>

  </div>
  <div class="recent-messages">
    <h3>Your Messages</h3>
    <% if (inbox == null || inbox.isEmpty()) { %>
    <p>No messages.</p>
    <% } else { %>
    <ul class="message-list">
      <% for (Mail mail : inbox) { %>
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
        <a href="message.jsp?id=<%= mail.getId() %>" class="link link-blue">view message</a>
        <form action="DeleteMail" method="post" style="display:inline;">
          <input type="hidden" name="id" value="<%= mail.getId() %>" />
          <button type="submit" class="btn btn-red" style="margin-left:10px;">Delete</button>
        </form>
      </li>
      <% } %>
    </ul>
    <% } %>
  </div>

  <div class="mail-links mt-20 mb-20">
    <a href="sent.jsp" class="link link-blue">View Sent</a>
    <a href="compose.jsp" class="link link-blue">Compose New</a>
  </div>

  <div class="bottom-bar mt-30">
    <a href="homepage.jsp" class="link link-blue">Home</a>
  </div>
</div>
</body>
</html>

<a href="compose.jsp">Compose New</a> |
<a href="SentServlet">View Sent</a> |
<a href="Homepage">Home</a>
</body>
</html>

