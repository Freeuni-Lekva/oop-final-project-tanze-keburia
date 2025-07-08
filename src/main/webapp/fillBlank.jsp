<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="classes.quiz_utilities.Question" %>
<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
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
%>

<html>
<head>
  <title>Edit Fill in the Blank Question</title>
</head>
<body>

<h2>Edit Fill in the Blank</h2>

<form action="ModifyQuestion" method="post">
  <input type="hidden" name="questionID" value="<%= questionId %>">
  <input type="hidden" name="quizID" value="<%= quizId %>">

  <label for="statement">Statement (use '____' for the blank):</label><br>
  <textarea id="statement" name="statement" rows="3" cols="50"><%= question.getStatement() %></textarea><br><br>

  <label for="answer">Correct Answer:</label><br>
  <input type="text" id="answer" name="answer" value="<%= question.getAnswer() %>"><br><br>

  <input type="submit" value="Save">
</form>

<br>
<a href="ConfigureQuiz?id=<%= quizId %>">Back to Quiz Configuration</a>

</body>
</html>




