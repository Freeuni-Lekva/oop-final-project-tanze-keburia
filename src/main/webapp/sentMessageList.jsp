<%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 07-Jul-25
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--</body>--%>
<%--</html>--%>



<%@ page import="classes.mail.Mail" %>
<%@ page import="java.util.List" %>
<%
  String currentUser = (String) request.getAttribute("currentUser");
  String otherUser = (String) request.getAttribute("otherUser");
  List<Mail> conversation = (List<Mail>) request.getAttribute("conversation");
%>
<html>
<head><title>Sent Messages to <%= otherUser %></title></head>
<body>
<h2>Sent Messages to <%= otherUser %></h2>

<% if (conversation == null || conversation.isEmpty()) { %>
<p>No sent messages found to <%= otherUser %>.</p>
<% } else { %>
<ul>
  <% for (Mail mail : conversation) { %>
  <li>
    <strong>To:</strong> <%= mail.getReceiver() %><br/>
    <strong>Subject:</strong> <%= mail.getSubject() %><br/>
    <strong>Time:</strong> <%= mail.getTimestamp() %><br/>
    <a href="ViewMail?id=<%= mail.getId() %>">View Message</a><br/>
    <hr/>
  </li>
  <% } %>
</ul>
<% } %>

<br/>
<a href="SentServlet">Back to Sent</a> |
<a href="compose.jsp">Compose New</a> |
<a href="Homepage">Home</a>
</body>
</html>