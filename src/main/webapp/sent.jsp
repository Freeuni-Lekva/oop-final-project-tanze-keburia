<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.Mail" %>
<%@ page import="java.util.List" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Mail> sent = (List<Mail>) request.getAttribute("sentMails");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sent Mails</title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Sent Messages</h2>
        <a href="MyProfileServlet" class="link link-blue">My Profile</a>
    </div>

    <!-- Search Form for History -->
    <form method="get" action="MessageHistoryServlet" class="search-form">
        <input type="hidden" name="messageType" value="sent" />
        <input type="text" name="otherUser" placeholder="Enter username to view conversation" required />
        <input type="submit" value="search" class="btn btn-blue" />
    </form>

    <div class="recent-messages mt-20">
        <h3>Your Sent Messages</h3>

        <% if (sent == null || sent.isEmpty()) { %>
        <p>No sent messages.</p>
        <% } else { %>
        <ul class="message-list">
            <% for (Mail mail : sent) { %>
            <li class="card message-card">
                <div>
                    <strong>To:</strong> <%= mail.getReceiver() %><br/>
                    <strong>Subject:</strong> <%= mail.getSubject() %>
                </div>
                <%
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedTime = sdf.format(mail.getTimestamp());
                %>
                <div class="timestamp"><%= formattedTime %></div>

                <div style="margin-top: 10px;">
                    <a href="ViewMail?id=<%= mail.getId() %>" class="link link-blue">View Message</a>
                    <form action="DeleteSentMail" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= mail.getId() %>" />
                        <button type="submit" class="btn btn-red" style="margin-left:10px;">Delete</button>
                    </form>
                </div>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="mail-links mt-20 mb-20">
        <a href="InboxServlet" class="link link-blue">View Inbox</a>
        <a href="compose.jsp" class="link link-blue">Compose New</a>
    </div>

    <div class="bottom-bar mt-30">
        <a href="Homepage" class="link link-blue">Home</a>
    </div>
</div>
</body>
</html>
