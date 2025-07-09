<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.Quiz" %>
<html>
<head>
  <title>All Quizzes</title>
</head>
<body>

<h2>All Available Quizzes</h2>

<%
  List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");

  if (quizzes == null || quizzes.isEmpty()) {
%>
<p>No quizzes found.</p>
<%
} else {
%>
<ul>
  <%
    for (Quiz quiz : quizzes) { %>
  <li>
    <a href="startQuiz?id=<%= quiz.getID() %>"><%= quiz.getName() %></a>
    — Author: <%= quiz.getAuthor() %>
  </li>
  <% }

  %>

</ul>
<%
  }
%>

<br>
<a href="homepage.jsp">Back to Homepage</a>

</body>
</html>
