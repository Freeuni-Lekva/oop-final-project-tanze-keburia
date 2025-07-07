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
  MailDAO mailDAO = (MailDAO) application.getAttribute("mails");
  List<Mail> inbox = mailDAO.getInbox(username);
%>
<html>
<head><title>Inbox</title></head>
<body>
<h2>Inbox</h2>
<% if (inbox.isEmpty()) { %>
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
    <hr/>
  </li>
  <% } %>
</ul>
<% } %>


<!-- Message History Section -->
<h3>View Message History</h3>
<form method="get" action="MessageHistoryServlet">
  <input type="hidden" name="currentUser" value="<%= username %>" />
  <label for="otherUser">Enter username to view message history:</label>
  <input type="text" id="otherUser" name="otherUser" placeholder="Username" required />
  <input type="submit" value="View Messages" />
</form>
<br/>



<a href="compose.jsp">Compose New</a> |
<a href="sent.jsp">View Sent</a> |
<a href="homepage.jsp">Home</a>
</body>
</html>
