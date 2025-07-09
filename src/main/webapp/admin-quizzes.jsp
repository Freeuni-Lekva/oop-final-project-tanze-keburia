<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.RealQuiz" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Quizzes</title>
</head>
<body>
<h1>Manage Quizzes</h1>

<p>Welcome, <%= request.getAttribute("adminUsername") %></p>


<p>
    <a href="AdminDashboardServlet">Dashboard</a> |
</p>

<%
    String success = (String) request.getAttribute("success");
    if (success != null) { %>
<p>Success: <%= success %></p>
<% } %>

<h2>All Quizzes</h2>
<%
    List<RealQuiz> quizzes = (List<RealQuiz>) request.getAttribute("quizzes");
    if (quizzes != null) {
        for (RealQuiz quiz : quizzes) {
%>
<div>
    <h3><%= quiz.getName() %></h3>
    <p>ID: <%= quiz.getID() %>, Author: <%= quiz.getAuthor() %></p>

    <form method="post" action="AdminQuizzesServlet">
        <input type="hidden" name="action" value="deleteQuiz">
        <input type="hidden" name="quizId" value="<%= quiz.getID() %>">
        <button type="submit">Delete Quiz</button>
    </form>
</div>
<hr>
<%
    }
} else {
%>
<p>No quizzes found</p>
<% } %>
</body>
</html>