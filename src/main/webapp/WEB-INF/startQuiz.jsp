<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Quiz" %>
<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    int questionCount = ((Integer) request.getAttribute("questionCount")).intValue();
%>


<html>
<head>
    <title><%= quiz.getName() %></title>
</head>
<body>

<h2>Quiz: <%= quiz.getName() %></h2>

<p><strong>Type:</strong> <%= quiz.getType() %></p>
<p><strong>Number of Questions:</strong> <%= questionCount %></p>
<p><strong>Time Limit:</strong>
    <%= quiz.getTimeLimit() == 0 ? "No limit" : (quiz.getTimeLimit() / 60) + " minutes and " + (quiz.getTimeLimit() % 60) + " seconds"%>
</p>
<p><strong>Topic:</strong> <%= quiz.getTopic() %></p>

<form action="StartActualQuizServlet" method="get">
    <input type="hidden" name="id" value="<%= quiz.getID() %>">
    <button type="submit">Start Quiz</button>
</form>

<br>
<a href="viewAllQuizzes">Back to All Quizzes</a>

</body>
</html>



