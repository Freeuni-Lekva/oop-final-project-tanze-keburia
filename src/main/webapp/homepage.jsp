<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.mail.Mail" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<%@ page import="classes.achievement.Achievement" %>


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
    List<Quiz> recentCreatedQuizzes = (List<Quiz>) request.getAttribute("recentCreatedQuizzes");
    List<Quiz> recentQuizzes = (List<Quiz>) request.getAttribute("recentQuizzes");
    List<Quiz> popularQuizzes = (List<Quiz>) request.getAttribute("popularQuizzes");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
    <style>
        .quiz-sections-container {
            display: flex;
            gap: 20px;
            justify-content: space-between;
            margin-top: 30px;
        }

        .quiz-box {
            flex: 1;
            background-color: #f5f8ff;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
        }

        .quiz-box h3 {
            margin-top: 0;
            border-bottom: 1px solid #ccc;
            padding-bottom: 5px;
        }

        .quiz-list {
            list-style: none;
            padding-left: 0;
        }

        .quiz-card {
            background-color: white;
            border-radius: 8px;
            padding: 10px 15px;
            margin-bottom: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }

        .quiz-meta-box {
            margin-top: 5px;
            font-size: 0.85em;
            color: #555;
        }

        .inbox-wrapper {
            position: relative;
            display: inline-block;
            margin-right: 15px;
        }


        .inbox-icon {
            width: 24px;
            height: 24px;
            display: inline-block;
        }

        .unread-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: red;
            color: white;
            border-radius: 50%;
            padding: 1px 6px;
            font-size: 12px;
            font-weight: bold;
            line-height: 1;
        }

        .icon-badge-container {
            position: relative;
            display: inline-block;
            margin-right: 6px;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <div style="position: relative; margin-bottom: 20px;">
        <img src="assets/welcome.webp" alt="Welcome Banner" style="max-width: 100%; height: auto;" />

        <div class="speech-bubble">
            <div class="speech-text">
                Welcome <%= username %>!
            </div>
        </div>



        <div style="text-align: right; margin-bottom: 20px;">
        <a href="MyProfileServlet" class="link link-blue" style="font-weight: 500;">My Profile</a>
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

    <a href="ViewChallenges?username=<%=username%>" class="link link-purple">View Challenges</a>

    <div class="quiz-sections-container">
        <!-- Recent Messages Block -->
        <div class="quiz-box">
            <h3>Recent Messages</h3>
            <% if (inboxPreview == null || inboxPreview.isEmpty()) { %>
            <p>No new messages</p>
            <% } else { %>
            <ul class="quiz-list">
                <% int shown = 0;
                    for (Mail mail : inboxPreview) {
                        if (shown >= 3) break;
                        shown++; %>
                <li class="quiz-card">
                    <strong>From:</strong> <%= mail.getSender() %><br>
                    <strong>Subject:</strong> <%= mail.getSubject() %><br>
                    <%
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String formattedTime = sdf.format(mail.getTimestamp());
                    %>
                    <div class="quiz-meta-box"><%= formattedTime %></div>
                    <a href="ViewMail?id=<%= mail.getId() %>" class="link link-blue">View Message</a>
                </li>
                <% } %>
            </ul>
            <% } %>
        </div>

        <!-- Latest Announcement Block -->
        <div class="quiz-box">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <h3 style="margin: 0;">Latest Announcement</h3>
                <a href="AllAnnouncementsServlet" class="link link-purple">All Announcements</a>
            </div>
            <%
                Announcement latestAnnouncement = (Announcement) request.getAttribute("latestAnnouncement");
                if (latestAnnouncement != null) {
            %>
            <div class="quiz-card" style="margin-top: 10px;">
                <p><%= latestAnnouncement.getBody() %></p>
                <div class="quiz-meta-box">
                    Posted on:
                    <%
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String formattedTime = sdf.format(latestAnnouncement.getPublishDate());
                    %>
                    <%= formattedTime %>
                </div>
            </div>
            <% } else { %>
            <p style="margin-top: 10px;">No announcements yet</p>
            <% } %>
        </div>
    </div>


    <div class="mail-links mt-20 mb-20">
    <div class="inbox-wrapper">
        <a href="InboxServlet" class="link link-blue inbox-link">
            <div class="icon-badge-container">
                <img src="assets/message-icon.webp" alt="Inbox" class="inbox-icon" />
                <% Integer unreadCount = (Integer) request.getAttribute("unreadCount");
                    if (unreadCount != null && unreadCount > 0) { %>
                <div class="unread-badge"><%= unreadCount %></div>
                <% } %>
            </div>
            Inbox
        </a>
    </div>


    <a href="SentServlet" class="link link-blue">
        <img src="assets/sent-icon.webp" alt="Sent" class="inbox-icon" />
        Sent
    </a>
    </div>

    <div class="quiz-sections-container">
        <div class="quiz-box">
            <h3>Most Recent Quizzes</h3>
            <% if (recentQuizzes != null && !recentQuizzes.isEmpty()) { %>
            <ul class="quiz-list">
                <% for (Quiz quiz : recentQuizzes) { %>
                <li class="quiz-card">
                    <a href="startQuiz?id=<%= quiz.getID() %>" class="link link-blue">
                        <%= quiz.getName() %>
                    </a>
                    <div class="quiz-meta-box">
                        by <%= quiz.getAuthor() %><br>
                        Created at <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(quiz.getCreationDate()) %>
                    </div>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p>No recent quizzes available.</p>
            <% } %>
        </div>

        <div class="quiz-box">
            <h3>Most Popular Quizzes</h3>
            <% if (popularQuizzes != null && !popularQuizzes.isEmpty()) { %>
            <ul class="quiz-list">
                <% for (Quiz quiz : popularQuizzes) { %>
                <li class="quiz-card">
                    <a href="startQuiz?id=<%= quiz.getID() %>" class="link link-blue">
                        <%= quiz.getName() %>
                    </a>
                    <div class="quiz-meta-box">
                        Played <%= quiz.getPlayCount() %> times
                    </div>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p>No popular quizzes available.</p>
            <% } %>
        </div>

        <div class="quiz-box">
            <h3>Quizzes You Created Recently</h3>
            <% if (recentCreatedQuizzes != null && !recentCreatedQuizzes.isEmpty()) { %>
            <ul class="quiz-list">
                <% for (Quiz quiz : recentCreatedQuizzes) { %>
                <li class="quiz-card">
                    <a href="startQuiz?id=<%= quiz.getID() %>" class="link link-blue">
                        <%= quiz.getName() %>
                    </a>
                    <div class="quiz-meta-box">
                        Created at <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(quiz.getCreationDate()) %>
                    </div>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p>You haven't created any quizzes yet.</p>
            <% } %>
        </div>
    </div>

    <div class="bottom-bar mt-30">
        <a href="logout.jsp" class="link link-red">Log out</a>
        <form action="compose.jsp" method="get" class="bottom-right">
            <button type="submit" class="btn btn-purple">Compose</button>
        </form>
    </div>
    <% List<Achievement> achievements = (List<Achievement>) request.getAttribute("achievements");
        if (achievements != null && !achievements.isEmpty()) { %>
    <h3>Achievements</h3>
    <ul class="achievement-list">
        <% for (Achievement ach : achievements) { %>
        <li class="card achievement-card">
            <strong><%= ach.getType() %></strong><br/>
            <small>Awarded at: <%= ach.getAwardedAt() %></small>
        </li>
        <% } %>
    </ul>
    <% } else { %>
    <p>No achievements yet.</p>
    <% } %>
</div>

</body>
</html>
