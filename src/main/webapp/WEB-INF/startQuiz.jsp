<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.Quiz" %>
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
        <%
    int timeLimit = quiz.getTimeLimit();
    String timeDisplay;
    if (timeLimit == 0) {
        timeDisplay = "No limit";
    } else if (timeLimit < 60) {
        timeDisplay = timeLimit + " seconds";
    } else if (timeLimit % 60 == 0) {
        timeDisplay = (timeLimit / 60) + " minutes";
    } else {
        timeDisplay = (timeLimit / 60) + " minutes and " + (timeLimit % 60) + " seconds";
    }
%>

<p><strong>Time Limit:</strong> <%= timeDisplay %></p>

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



