<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<%@ page import="classes.quiz_result.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>

<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    int questionCount = ((Integer) request.getAttribute("questionCount")).intValue();
    List<QuizResult> leaderboard = (List<QuizResult>) request.getAttribute("leaderboard");

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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= quiz.getName() %></title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard full-height">
    <div class="quiz-center-container vertical-align">
        <div class="quiz-details-container">
            <h2 class="page-title">Quiz: <%= quiz.getName() %></h2>

            <p><strong>Type:</strong> <%= quiz.getType() %></p>
            <p><strong>Number of Questions:</strong> <%= questionCount %></p>
            <p><strong>Time Limit:</strong> <%= timeDisplay %></p>
            <p><strong>Topic:</strong> <%= quiz.getTopic() %></p>

            <form action="StartActualQuizServlet" method="get" class="start-quiz-form">
                <input type="hidden" name="id" value="<%= quiz.getID() %>">
                <button type="submit" class="btn btn-blue">Start Quiz</button>
            </form>

            <h3 class="leaderboard-title">Leaderboard</h3>
            <% if (leaderboard != null && !leaderboard.isEmpty()) { %>
            <table class="leaderboard-table">
                <thead>
                <tr>
                    <th>Rank</th>
                    <th>User</th>
                    <th>Score</th>
                    <th>Submit Time</th>
                </tr>
                </thead>
                <tbody>
                <%
                    int rank = 1;
                    for (QuizResult entry : leaderboard) {
                        String user = entry.getUsername();
                        Double score = entry.getScore();
                        Timestamp time = entry.getSubmitTime();
                %>
                <tr>
                    <td><%= rank++ %></td>
                    <td><a href="ProfileServlet?username=<%= user %>"><%= user %></a></td>
                    <td><%= score %></td>
                    <td><%= time %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-quizzes-msg">No participants yet.</p>
            <% } %>

            <div class="back-button-container">
                <a href="viewAllQuizzes" class="btn btn-gray">Back to All Quizzes</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
