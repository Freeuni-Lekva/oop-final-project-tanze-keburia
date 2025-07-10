<%@ page import="classes.mail.Mail" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 07-Jul-25
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String currentUser = (String) request.getAttribute("currentUser");
    String otherUser = (String) request.getAttribute("otherUser");
    List<Mail> conversation = (List<Mail>) request.getAttribute("conversation");
%>
<html>
<head><title>Messages with <%= otherUser %></title></head>
<body>
<h2>Messages with <%= otherUser %></h2>

<% if (conversation == null || conversation.isEmpty()) { %>
<p>No messages found with <%= otherUser %>.</p>
<% } else { %>
<ul>
    <% for (Mail mail : conversation) { %>
    <li>
        <strong>From:</strong> <%= mail.getSender() %><br/>
        <strong>Subject:</strong> <%= mail.getSubject() %><br/>
        <strong>Time:</strong> <%= mail.getTimestamp() %><br/>
        <a href="ViewMail?id=<%= mail.getId() %>">View Message</a><br/>
        <% if (mail.getReceiver().equals(currentUser)) { %>
        <form action="DeleteMail" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= mail.getId() %>" />
            <button type="submit">Delete</button>
        </form>
        <% } %>
        <hr/>
    </li>
    <% } %>
</ul>
<% } %>

<br/>
<a href="InboxServlet">Back to Inbox</a> |
<a href="compose.jsp">Compose New</a> |
<a href="Homepage">Home</a>
</body>
</html>