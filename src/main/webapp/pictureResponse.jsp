<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>

<%
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  String questionId = request.getParameter("id");
  String quizId = request.getParameter("quizID");
  Question question = (Question) request.getAttribute("question");

  if (question == null) {
%>
<p style="color:red;">Question not found.</p>
<a href="ConfigureQuiz?id=<%= quizId %>">Back</a>
<%
    return;
  }

  // Extract prompt and image URL from statement
  String[] parts = question.getStatement().split(";;");
  String prompt = parts.length > 0 ? parts[0] : "";
  String imageUrl = parts.length > 1 ? parts[1] : "";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Picture-Response Question</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">

  <div class="header-row">
    <h2>Edit Picture-Response Question</h2>
    <a href="Homepage" class="link link-blue">Home</a>
  </div>

  <div class="center-form">
    <form action="ModifyQuestion" method="post">
      <input type="hidden" name="questionID" value="<%= questionId %>">
      <input type="hidden" name="quizID" value="<%= quizId %>">

      <label for="prompt"><strong>Question Prompt:</strong></label>
      <input type="text" id="prompt" name="prompt" value="<%= prompt %>" class="input-full" required />

      <label for="imageUrl"><strong>Image URL:</strong></label>
      <input type="text" id="imageUrl" name="imageUrl" value="<%= imageUrl %>" class="input-full" required />

      <label for="answer"><strong>Correct Answer:</strong></label>
      <input type="text" id="answer" name="answer" value="<%= question.getAnswer() %>" class="input-full" required />

      <% if (!imageUrl.isEmpty()) { %>
      <p><strong>Preview:</strong></p>
      <img src="<%= imageUrl %>" alt="Question Image" width="300" style="margin-bottom:15px;"><br>
      <% } %>

      <input type="submit" value="Save" class="btn btn-green" />
    </form>
  </div>

  <div class="bottom-bar mt-30">
    <a href="ConfigureQuiz?id=<%= quizId %>" class="link link-blue">Back to Quiz Configuration</a>
  </div>

</div>
</body>
</html>
