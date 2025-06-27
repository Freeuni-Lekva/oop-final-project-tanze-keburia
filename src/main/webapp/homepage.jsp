<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="classes.Mail" %>
<%@ page import="database.MailDAO" %>
<%@ page import="java.util.List" %>
<%
    String username;
    if (session != null) {
        username = (String) session.getAttribute("username");
    } else {
        username = null;
    }

    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    MailDAO mailDAO = (MailDAO) application.getAttribute("mails");
    List<Mail> inboxPreview = mailDAO.getInbox(username);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>

<h2>Welcome, <%= username %>!</h2>

<h3>Search for a User</h3>
<form method="get" action="SearchServlet">
    <input type="text" name="username" placeholder="Enter username" required />
    <input type="submit" value="Search" />
</form>
<a href="myProfile.jsp">My Profile</a> |
<a href="logout.jsp">Log out</a>
<div>
    <a href="compose.jsp">
        <button type="button">Compose</button>
    </a>
</div>
<h3>Recent Messages</h3>
<% if (inboxPreview == null || inboxPreview.isEmpty()) { %>
<p>No new messages</p>
<% } else { %>
<ul>
    <% int shown = 0;
        for (Mail mail : inboxPreview) {
            if (shown >= 3) break;
            shown++; %>
    <li>
        <strong>From:</strong> <%= mail.getSender() %><br/>
        <strong>Subject:</strong> <%= mail.getSubject() %><br/>
        <small><%= mail.getTimestamp() %></small><br/>
        <a href="message.jsp?id=<%= mail.getId() %>">View Message</a><br/>
        <hr/>
    </li>
    <% } %>
</ul>
<% } %>
<a href="inbox.jsp">View Inbox</a>
<div>
    <a href="sent.jsp">View Sent Mails</a>
</div>
</body>
</html>
<jsp:include page = "createQuizButton.jspf"/>
