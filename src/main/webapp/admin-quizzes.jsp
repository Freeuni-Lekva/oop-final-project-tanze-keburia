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
<a href="logout">Logout</a>

<nav>
    <a href="admin-dashboard">Dashboard</a>
    <a href="admin-announcements">Manage Announcements</a>
    <a href="admin-users">Manage Users</a>
    <a href="admin-quizzes">Manage Quizzes</a>
</nav>

<%
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
    if (success != null) {
%>
<div>Success: <%= success %></div>
<% } %>
<% if (error != null) { %>
<div>Error: <%= error %></div>
<% } %>

<h3>Filter Quizzes</h3>
<form method="get" action="admin-quizzes">
    <label for="searchTerm">Search:</label>
    <input type="text" name="searchTerm" id="searchTerm"
           placeholder="Search by title or author..."
           value="<%= request.getParameter("searchTerm") != null ? request.getParameter("searchTerm") : "" %>">

    <label for="filterBy">Filter By:</label>
    <select name="filterBy" id="filterBy">
        <option value="all" <%= "all".equals(request.getParameter("filterBy")) ? "selected" : "" %>>All Quizzes</option>
        <option value="topic" <%= "topic".equals(request.getParameter("filterBy")) ? "selected" : "" %>>By Topic</option>
        <option value="author" <%= "author".equals(request.getParameter("filterBy")) ? "selected" : "" %>>By Author</option>
        <option value="type" <%= "type".equals(request.getParameter("filterBy")) ? "selected" : "" %>>By Type</option>
    </select>

    <button type="submit">Filter</button>
</form>

<h2>All Quizzes</h2>
<%
    List<RealQuiz> quizzes = (List<RealQuiz>) request.getAttribute("quizzes");
    if (quizzes != null && !quizzes.isEmpty()) {
        for (RealQuiz quiz : quizzes) {
%>
<div>
    <h4><%= quiz.getName() %></h4>
    <p>
        <strong>ID:</strong> <%= quiz.getID() %> |
        <strong>Author:</strong> <%= quiz.getAuthor() %> |
        <strong>Topic:</strong> <%= quiz.getTopic() %> |
        <strong>Type:</strong> <%= quiz.getType() %>
    </p>
    <p><strong>Created:</strong> <%= quiz.getCreationDate() %></p>
    <p><strong>Questions:</strong> <%= quiz.getNumQuestions() %></p>

    <a href="quiz-details?id=<%= quiz.getID() %>">View Details</a>

    <form method="post" action="admin-quizzes" style="display: inline;">
        <input type="hidden" name="action" value="clearHistory">
        <input type="hidden" name="quizId" value="<%= quiz.getID() %>">
        <button type="submit" onclick="return confirm('Are you sure you want to clear all history for this quiz?')">
            Clear History
        </button>
    </form>

    <form method="post" action="admin-quizzes" style="display: inline;">
        <input type="hidden" name="action" value="deleteQuiz">
        <input type="hidden" name="quizId" value="<%= quiz.getID() %>">
        <button type="submit" onclick="return confirm('Are you sure you want to delete this quiz? This action cannot be undone.')">
            Delete Quiz
        </button>
    </form>

    <hr>
</div>
<%
    }
} else {
%>
<div>
    <h3>No quizzes found</h3>
    <p>There are currently no quizzes in the system.</p>
</div>
<% } %>
</body>
</html>