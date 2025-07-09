<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/7/2025
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Quiz Summary</title>
</head>
<body>

<h2>Quiz Completed</h2>

<p><strong>Time Taken:</strong>
  <%= request.getAttribute("timeTaken") != null
          ? request.getAttribute("timeTaken")
          : "To be calculated" %>
</p>


<%
  Object scoreObj = request.getAttribute("totalScore");
  double score = (scoreObj instanceof Double) ? (Double) scoreObj : 0.0;
%>
<p><strong>Your Score:</strong> <%= score %></p>

<a href="viewAllQuizzes"><button type="button">Back to Quiz List</button></a>
</body>
</html>