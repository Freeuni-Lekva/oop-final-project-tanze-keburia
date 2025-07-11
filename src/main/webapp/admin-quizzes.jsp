<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.quiz.RealQuiz" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Manage Quizzes</title>
  <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
  <div class="header-row">
    <h2>Manage Quizzes</h2>
    <a href="AdminDashboardServlet" class="link link-blue">Dashboard</a>
  </div>

  <% String success = (String) request.getAttribute("success"); %>
  <% if (success != null) { %>
  <div class="success-message"><%= success %></div>
  <% } %>

  <div class="recent-messages mt-20">
    <h3>All Quizzes</h3>

    <%
      List<RealQuiz> quizzes = (List<RealQuiz>) request.getAttribute("quizzes");
      if (quizzes != null && !quizzes.isEmpty()) {
    %>
    <ul class="message-list">
      <% for (RealQuiz quiz : quizzes) { %>
      <li class="card message-card">
        <div>
          <strong>Quiz Name:</strong> <%= quiz.getName() %><br/>
          <strong>Author:</strong> <%= quiz.getAuthor() %>
        </div>

        <div style="margin-top: 10px;">
          <form method="get" action="StartActualQuizServlet" style="display:inline;">
            <input type="hidden" name="id" value="<%= quiz.getID() %>">
            <button type="submit" class="btn btn-green">Play Quiz</button>
          </form>

          <form method="post" action="AdminQuizzesServlet"
                onsubmit="return confirm('Are you sure you want to delete this quiz?');"
                style="display:inline; margin-left: 10px;">
            <input type="hidden" name="action" value="deleteQuiz">
            <input type="hidden" name="quizId" value="<%= quiz.getID() %>">
            <button type="submit" class="btn btn-red">Delete Quiz</button>
          </form>
        </div>
      </li>
      <% } %>
    </ul>
    <% } else { %>
    <p>No quizzes found.</p>
    <% } %>
  </div>

  <div class="bottom-bar mt-30">
    <a href="Homepage" class="link link-blue">Home</a>
  </div>
</div>
</body>

</html>