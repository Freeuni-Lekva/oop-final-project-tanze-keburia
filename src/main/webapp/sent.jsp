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
    List<Mail> sent = mailDAO.getSent(username);
%>
<html>
<head><title>Sent Messages</title></head>
<body>
<h2>Sent</h2>
<% if (sent.isEmpty()) { %>
<p>No sent messages.</p>
<% } else { %>
<ul>
    <% for (Mail mail : sent) { %>
    <li>
        <strong>To:</strong> <%= mail.getReceiver() %><br/>
        <strong>Subject:</strong> <%= mail.getSubject() %><br/>
        <strong>Time:</strong> <%= mail.getTimestamp() %><br/>
        <a href="message.jsp?id=<%= mail.getId() %>">View Message</a><br/>
        <form action="DeleteSentMail" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= mail.getId() %>" />
            <button type="submit">Delete</button>
        </form>
        <hr/>
    </li>

    <% } %>
</ul>
<% } %>
<a href="compose.jsp">Compose New</a> |
<a href="inbox.jsp">Inbox</a> |
<a href="homepage.jsp">Home</a>
</body>
</html>
