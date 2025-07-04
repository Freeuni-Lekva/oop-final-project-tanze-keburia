<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Quiz" %>

<html>
<head>
    <title>Start Quiz</title>
</head>
<body>

<%
  Quiz quiz = (Quiz) request.getAttribute("quiz");
  if (quiz == null) {
%>
  <p>Quiz not found.</p>
<%
} else {
%>
  <h2>Quiz: <%= quiz.getName() %></h2>
  <p>Author: <%= quiz.getAuthor() %></p>
  <!-- Here I'll add questions or "Start" button -->
<%
  }
%>

<br>
<a href="homepage.jsp">Back to Homepage</a>


</body>
</html>
