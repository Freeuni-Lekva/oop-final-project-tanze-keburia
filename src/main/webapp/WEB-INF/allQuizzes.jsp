<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>All Quizzes</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard full-height">
  <div class="quiz-center-container vertical-align">
    <div class="quiz-list-container">
      <h2 class="page-title">All Available Quizzes</h2>

      <%
        List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");
        if (quizzes == null || quizzes.isEmpty()) {
      %>
      <p class="no-quizzes-msg">No quizzes found.</p>
      <%
      } else {
      %>
      <ul class="quiz-list">
        <% for (Quiz quiz : quizzes) { %>
        <li class="quiz-item">
          <a class="quiz-link" href="startQuiz?id=<%= quiz.getID() %>"><%= quiz.getName() %></a>
          <span class="quiz-author"> — Author: <%= quiz.getAuthor() %></span>
        </li>
        <% } %>
      </ul>
      <% } %>

      <div class="back-button-container">
        <a href="Homepage" class="btn btn-blue">Back to Homepage</a>
      </div>
    </div>
  </div>
</div>
</body>
</html>
