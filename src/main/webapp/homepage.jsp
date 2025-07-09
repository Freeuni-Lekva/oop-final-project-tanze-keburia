<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.Mail" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.Announcement" %>
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
    List<Mail> inboxPreview = (List<Mail>) request.getAttribute("inboxPreview");

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Welcome, <%= username %>!</h2>
        <a href="MyProfileServlet" class="link link-blue">My Profile</a>
    </div>

    <form method="get" action="SearchServlet" class="search-form">
        <input type="text" name="username" placeholder="Enter username" required />
        <input type="submit" value="Search" class="btn btn-blue" />
    </form>

    <div class="quiz-actions">
        <form action="viewAllQuizzes" method="get">
            <button type="submit" class="btn btn-green">View All Quizzes</button>
        </form>
        <form action="CreateQuiz" method="post">
            <button type="submit" class="btn btn-green">Create Quiz</button>
        </form>
    </div>


    <div class="latest-announcement mt-20">
        <div class="announcement-header-with-link">
            <h3>Latest Announcement</h3>
            <a href="AllAnnouncementsServlet" class="link link-purple">All Announcements</a>
        </div>
        <%
            Announcement latestAnnouncement = (Announcement) request.getAttribute("latestAnnouncement");
            if (latestAnnouncement != null) {
        %>
        <div class="card announcement-card">
            <p><%= latestAnnouncement.getBody() %></p>
            <div class="timestamp">
                Posted on:
                <%
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedTime = sdf.format(latestAnnouncement.getPublishDate());
                %>
                <%= formattedTime %>
            </div>
        </div>
        <% } else { %>
        <p>No announcements yet</p>
        <% } %>
    </div>


    <div class="recent-messages mt-20">
        <h3>Recent Messages</h3>
        <% if (inboxPreview == null || inboxPreview.isEmpty()) { %>
        <p>No new messages</p>
        <% } else { %>
        <ul class="message-list">
            <% int shown = 0;
                for (Mail mail : inboxPreview) {
                    if (shown >= 3) break;
                    shown++; %>
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
                <a href="message.jsp?id=<%= mail.getId() %>" class="link link-blue">View Message</a>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="mail-links mt-20 mb-20">
        <a href="InboxServlet" class="link link-blue">View Inbox</a>
        <a href="SentServlet" class="link link-blue">View Sent Mails</a>
    </div>

    <div class="bottom-bar mt-30">
        <a href="logout.jsp" class="link link-red">Log out</a>
        <form action="compose.jsp" method="get" class="bottom-right">
            <button type="submit" class="btn btn-purple">Compose</button>
        </form>
    </div>
</div>
</body>
</html>
