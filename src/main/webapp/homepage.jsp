<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/18/2025
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>


<%@ page import="java.util.List" %>
<%@ page import="classes.mail.Mail" %>
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

<%
    response.setContentType("text/html; charset=UTF-8");
%>


<!DOCTYPE html>
<html>
<head>
    <script>
        function confirmStartQuiz(quizId) {
            const confirmed = confirm("Do you want to start this quiz?");
            if (confirmed) {
                window.location.href = "StartActualQuizServlet?id=" + encodeURIComponent(quizId);
            }
        }
    </script>


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
                <a href="ViewMail?id=<%= mail.getId() %>" class="link link-blue">View Message</a>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>

    <div class="mail-links mt-20 mb-20">
        <a href="InboxServlet" class="link link-blue">View Inbox</a>
        <a href="SentServlet" class="link link-blue">View Sent Mails</a>
    </div>

    <%@ page import="classes.quiz_utilities.quiz.Quiz" %>
    <%
        List<Quiz> recentQuizzes = (List<Quiz>) request.getAttribute("recentQuizzes");
        List<Quiz> popularQuizzes = (List<Quiz>) request.getAttribute("popularQuizzes");
    %>

    <div class="quiz-section mt-20">
        <h3>Most Recent Quizzes</h3>
        <% if (recentQuizzes != null && !recentQuizzes.isEmpty()) { %>
        <ul class="quiz-list">
            <% for (Quiz quiz : recentQuizzes) { %>
            <li class="card quiz-card">
                <a href="#" class="link link-blue"
                   onclick="confirmStartQuiz('<%= quiz.getID() %>'); return false;">
                    <%= quiz.getName() %>
                </a>

                </a>
                <div class="quiz-meta">
                    by <%= quiz.getAuthor() %> — <%= quiz.getCreationDate() %>
                </div>
            </li>
            <% } %>
        </ul>
        <% } else { %>
        <p>No recent quizzes available.</p>
        <% } %>
    </div>

    <div class="quiz-section mt-20">
        <h3>Most Popular Quizzes</h3>
        <% if (popularQuizzes != null && !popularQuizzes.isEmpty()) { %>
        <ul class="quiz-list">
            <% for (Quiz quiz : popularQuizzes) { %>
            <li class="card quiz-card">
                <a href="#" class="link link-blue"
                   onclick="confirmStartQuiz('<%= quiz.getID() %>'); return false;">
                    <%= quiz.getName() %>
                </a>

                </a>
                <div class="quiz-meta">
                    Played <%= quiz.getPlayCount() %> times
                </div>
            </li>
            <% } %>
        </ul>
        <% } else { %>
        <p>No popular quizzes available.</p>
        <% } %>
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