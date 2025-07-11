<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mapper.TypePageMapper" %>
<%@ page import="mapper.Topics" %>
<%@ page import="java.util.*" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>

<%
    String quizID = (String)request.getAttribute("id");
    String quizName = (String)request.getAttribute("quizName");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    Quiz quiz = (Quiz)request.getAttribute("quiz");

    String timeLimitMessage = "Time limit not set";
    String quizType = "";
    if (quiz != null) {
        quizType = quiz.getType();
        timeLimitMessage = quiz.getTimeLimit() >= 1000000000
                ? "There is no current limit"
                : "Current time limit is " + quiz.getTimeLimit() + " seconds";
    }

    String backUrl = request.getHeader("referer");
    if (backUrl == null || backUrl.contains("AddQuestion") || backUrl.contains("ConfigureQuiz")) {
        backUrl = "AdminDashboardServlet";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Configure Quiz</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <button class="btn btn-blue" onclick="window.location.href='Homepage'">Back</button>
        <h2><%= quizName != null ? quizName : "Unnamed Quiz" %></h2>
    </div>

    <% if (quiz == null) { %>
    <div class="error-message">
        Error: Quiz information could not be loaded.
    </div>
    <% } else { %>
    <% if (questions == null || questions.isEmpty()) { %>
    <p class="no-quizzes">No questions yet</p>
    <% } else { %>
    <ul class="quiz-list">
        <% for (Question q : questions) {
            String questionURL = "/ViewQuestion" + "?id=" + q.getID() + "&quizID=" + quizID; %>
        <li class="quiz-item card">
            <div class="quiz-info">
                <a href="<%= questionURL %>" class="link link-blue">View Question</a>
            </div>
            <form action="DeleteQuestion" method="post">
                <input type="hidden" name="quizID" value="<%= quizID %>">
                <input type="hidden" name="questionID" value="<%= q.getID() %>">
                <button type="submit" class="btn btn-red">Delete</button>
            </form>
        </li>
        <% } %>
    </ul>
    <% } %>

    <div class="quiz-actions">
        <form action="AddQuestion" method="post">
            <input type="hidden" name="type" value="<%= quizType %>">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <button type="submit" class="btn btn-green">Add Question</button>
        </form>
    </div>

    <div class="time-limit card">
        <p><%= timeLimitMessage %></p>
        <form action="SetTimeLimit" method="post">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <label for="timeLimit">Time Limit (seconds):</label>
            <input type="text" name="timeLimit" id="timeLimit" class="input-full">
            <button type="submit" class="btn btn-blue mt-20">Set Time Limit</button>
        </form>
    </div>

    <div class="publish-form card">
        <form id="publishQuizForm" action="PublishQuiz" method="post">
            <label for="topicDropdown">Choose Topic:</label>
            <select id="topicDropdown" name="topic" class="input-full">
                <% for (Topics t : Topics.values()) { %>
                <option value="<%= t.name() %>"><%= t.name().replace('_', ' ') %></option>
                <% } %>
            </select>
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <button type="submit" class="btn btn-purple mt-20">Publish Quiz</button>
        </form>
    </div>
    <% } %>
</div>
</body>
</html>