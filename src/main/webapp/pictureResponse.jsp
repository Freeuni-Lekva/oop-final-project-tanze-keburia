<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 01:36
--%>

<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);
  String questionId = request.getParameter("id");
  String quizId = request.getParameter("quizID");
  Question question = (Question)request.getAttribute("question");
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

<html>
<head>
  <title>Edit Picture-Response Question</title>
</head>
<body>

<h2>Edit Picture-Response Question</h2>

<form action="ModifyQuestion" method="post">
  <input type="hidden" name="questionID" value="<%= questionId %>">
  <input type="hidden" name="quizID" value="<%= quizId %>">

  <label for="prompt">Question Statement:</label><br>
  <input type="text" id="prompt" name="prompt" value="<%= prompt %>" size="60"><br><br>

  <label for="imageUrl">Image URL:</label><br>
  <input type="text" id="imageUrl" name="imageUrl" value="<%= imageUrl %>" size="60"><br><br>

  <label for="answer">Correct Answer:</label><br>
  <input type="text" id="answer" name="answer" value="<%= question.getAnswer() %>"><br><br>

  <p>Preview:</p>
  <img src="<%= imageUrl %>" alt="Question Image" width="300"><br><br>

  <input type="submit" value="Save">
</form>

<br>
<a href="ConfigureQuiz?id=<%= quizId %>">Back to Quiz Configuration</a>

</body>
</html>



