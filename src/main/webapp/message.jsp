<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.mail.Mail" %>
<%
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  String idParam = request.getParameter("id");
  if (idParam == null) {
    response.sendRedirect("inbox.jsp"); // fallback
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
  <title>View Message</title>
</head>
<body>
<h2>Message</h2>
<p><strong>From:</strong> <%= mail.getSender() %></p>
<p><strong>To:</strong> <%= mail.getReceiver() %></p>
<p><strong>Subject:</strong> <%= mail.getSubject() %></p>
<p><strong>Time:</strong> <%= mail.getTimestamp().toString() %></p>
<hr/>
<p><%= mail.getContent().replaceAll("\n", "<br/>") %></p>
<hr/>
<a href="inbox.jsp">Back to Inbox</a> |
<a href="sent.jsp">Back to Sent</a> |
<a href="homepage.jsp">Home</a>
</body>
</html>


