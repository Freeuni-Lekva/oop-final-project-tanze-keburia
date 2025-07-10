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

  <p>Welcome, <%= request.getAttribute("adminUsername") %></p>

  <%
    String success = (String) request.getAttribute("success");
    if (success != null) { %>
  <div class="success-message">
    <p><strong>Success:</strong> <%= success %></p>
  </div>
  <% } %>

  <h2>All Quizzes</h2>
  <%
    List<RealQuiz> quizzes = (List<RealQuiz>) request.getAttribute("quizzes");
    if (quizzes != null && !quizzes.isEmpty()) {
      for (RealQuiz quiz : quizzes) {
  %>
  <div class="card">
    <h3><%= quiz.getName() %></h3>
    <p><strong>Author:</strong> <%= quiz.getAuthor() %></p>

    <form method="post" action="AdminQuizzesServlet" onsubmit="return confirm('Are you sure you want to delete this quiz?');">
      <input type="hidden" name="action" value="deleteQuiz">
      <input type="hidden" name="quizId" value="<%= quiz.getID() %>">
      <button type="submit" class="btn btn-red">Delete Quiz</button>
    </form>
  </div>
  <%
    }
  } else {
  %>
  <div class="card">
    <p>No quizzes found</p>
  </div>
  <% } %>
</div>
</body>
</html>