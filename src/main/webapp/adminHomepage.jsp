<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.mail.Mail" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.admin.Announcement" %>
<%@ page import="classes.achievement.Achievement" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>


<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Expires", "0"); // Proxies

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
  List<Achievement> achievements = (List<Achievement>) request.getAttribute("achievements");
%>

<!DOCTYPE html>
<html>
<head>
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
  </style>

  <meta charset="UTF-8">
  <title>Admin Home</title>
  <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div style="position: relative; margin-bottom: 20px;">
    <img src="assets/welcome.webp" alt="Welcome Banner" style="max-width: 100%; height: auto;" />
    <div class="speech-bubble">
      <div class="speech-text">Welcome admin</div>
    </div>
    <div style="text-align: center; margin-top: 10px; margin-bottom: 20px;">
      <a href="MyProfileServlet" class="link link-blue" style="display: inline-flex; align-items: center; gap: 6px; font-weight: 500;">
        <img src="assets/profile.webp" alt="Profile" style="width: 22px; height: 22px;" />
        My Profile
      </a>
    </div>
  </div>

  <div class="admin-actions">
    <h3>Admin Panel</h3>
    <div class="admin-links">
      <a href="AdminDashboardServlet" class="link link-purple">Dashboard
        <img src="assets/dashboard.webp" alt="Dashboard" style="width: 20px; height: 20px; vertical-align: middle; margin-right: 5px;" />
      </a>
    </div>

    <form method="get" action="SearchServlet" class="search-form">
    <input type="text" name="username" placeholder="Enter username" required />
    <input type="submit" value="Search" class="btn btn-blue" />
  </form>

  <div class="quiz-actions">
    <form action="AdminQuizzesServlet" method="get">
      <button type="submit" class="btn btn-green">View All Quizzes</button>
    </form>
    <form action="CreateQuiz" method="post">
      <button type="submit" class="btn btn-green">Create Quiz</button>
    </form>
  </div>

  <a href="ViewChallenges?username=<%=username%>" class="link link-purple">View Challenges</a>

  <div class="mail-section" style="display: flex; justify-content: space-between; align-items: flex-start; margin-top: 20px; margin-bottom: 20px;">
    <!-- Inbox & Sent Links -->
    <div class="mail-links" style="display: flex; flex-direction: column; font-size: 17px; gap: 10px;">

      <!-- Inbox with badge -->
      <div class="inbox-wrapper" style="display: flex; align-items: center;">
        <a href="InboxServlet" class="link link-blue inbox-link" style="display: flex; align-items: center; gap: 8px;">
          <div class="icon-badge-container" style="position: relative; display: inline-block;">
            <img src="assets/message-icon1.webp" alt="Inbox" style="width: 28px; height: 28px;" />
            <% Integer unreadCount = (Integer) request.getAttribute("unreadCount");
              if (unreadCount != null && unreadCount > 0) { %>
            <div style="position: absolute; top: -5px; right: -5px; background-color: red; color: white; border-radius: 50%; padding: 1px 6px; font-size: 12px; font-weight: bold; line-height: 1;">
              <%= unreadCount %>
            </div>
            <% } %>
          </div>
          <span>Inbox</span>
        </a>
      </div>

      <!-- Sent -->
      <div class="sent-wrapper" style="display: flex; align-items: center;">
        <a href="SentServlet" class="link link-blue" style="display: flex; align-items: center; gap: 8px;">
          <img src="assets/sent-icon1.webp" alt="Sent" style="width: 28px; height: 28px;" />
          <span>Sent</span>
        </a>
      </div>
    </div>

    <!-- Compose -->
    <form action="compose.jsp" method="get">
      <button type="submit" class="btn btn-blue">Compose</button>
    </form>
  </div>

  <div class="quiz-announcements-container">
    <!-- Recent Messages -->
    <div class="quiz-box equal-height">
      <h3>Recent Messages</h3>
      <% if (inboxPreview == null || inboxPreview.isEmpty()) { %>
      <div style="text-align: center;">
<%--        <h3>Recent Messages</h3>--%>
        <img src="assets/nomessages.webp" alt="No Messages" style="max-width: 100px; margin-bottom: 10px;" />
        <p style="font-size: 15px; font-weight: 500; color: #333; margin-top: 10px;">
          No new messages
        </p>
      </div>
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

    <!-- Latest Announcement -->
    <div class="quiz-box equal-height">
      <h3>Latest Announcement</h3>
      <%
        Announcement latestAnnouncement = (Announcement) request.getAttribute("latestAnnouncement");
        if (latestAnnouncement != null) {
      %>
      <div class="quiz-card">
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
      <div style="text-align: center;">
        <img src="assets/noannoucments.webp" alt="No Announcements" style="max-width: 100px; margin-bottom: 10px;" />
        <p style="font-size: 15px; font-weight: 500; color: #333; margin-top: 10px;">
          No announcements yet.
        </p>
      </div>
      <% } %>
    </div>

    <!-- All Announcements -->
    <div class="quiz-box equal-height">
      <h3>All Announcements</h3>
      <div style="text-align: center;">
        <img src="assets/annoucments.webp" alt="All Announcements" style="max-width: 100px; margin-bottom: 10px;" />
      </div>
      <a href="AllAnnouncementsServlet" class="btn btn-blue" style="border: none; text-decoration: none;">View All</a>
    </div>
  </div>

  <div class="quiz-sections-container">
    <!-- Achievements -->
    <div class="quiz-box" style="position: relative;">
      <img src="assets/achievement.webp"
           alt="Achievement Icon"
           style="position: absolute; top: 0px; right: 0px; width: 80px; height: auto;
         box-shadow: 0 2px 8px rgba(0,0,0,0.15); border-radius: 8px;" />

      <h3>Your Achievements</h3>
      <% if (achievements != null && !achievements.isEmpty()) { %>
      <ul class="quiz-list">
        <% for (Achievement ach : achievements) { %>
        <li class="quiz-card">
          <strong><%= ach.getType() %></strong><br/>
          <small>Awarded at: <%= ach.getAwardedAt() %></small>
        </li>
        <% } %>
      </ul>
      <% } else { %>
      <div style="text-align: center;">
        <p style="font-size: 15px; font-weight: 500; color: #333; margin-top: 10px;">
          No achievements yet.
        </p>
      </div>
      <% } %>
    </div>

    <!-- Most Popular Quizzes -->
    <div class="quiz-box" style="position: relative;">
      <img src="assets/populars.webp"
           alt="Popular Icon"
           style="position: absolute; top: 0px; right: 0px; width: 70px; height: auto;
         box-shadow: 0 2px 8px rgba(0,0,0,0.15); border-radius: 8px;" />

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
      <div style="text-align: center;">
        <p style="font-size: 15px; font-weight: 500; color: #333; margin-top: 10px;">
          No popular quizzes available.
        </p>
      </div>
      <% } %>
    </div>
  </div>



  <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 30px;">
    <div>
      <a href="logout.jsp" class="link link-red" style="display: inline-flex; align-items: center; gap: 6px; font-size: 16px;">
        <img src="assets/logout.webp" alt="Logout" style="width: 20px; height: 20px;" />
        Log out
      </a>
    </div>
  </div>
</div>
</body>
</html>