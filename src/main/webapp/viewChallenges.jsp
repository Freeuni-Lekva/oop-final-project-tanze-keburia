<%@ page import="classes.social.Challenge" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Challenges</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<%
    List<Challenge> challengeList = (List<Challenge>) request.getAttribute("challengeList");
%>
<h1>Your Challenges</h1>

<div class="challenge-icon-container">
    <img src="assets/challenge.webp" alt="Challenge Icon" class="challenge-icon">
</div>

<% if (challengeList != null && !challengeList.isEmpty()) { %>
<table class="challenge-table">
    <thead>
    <tr>
        <th>Quiz</th>
        <th>Sender</th>
        <th>Score</th>
        <th>Reject</th>
        <th>Accept</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Challenge entry : challengeList) {
            Double score = entry.getScore();
            String sender = entry.getSender();
            String id = entry.getQuizID();
            String quiz_name = entry.getQuizName();
    %>
    <tr>
        <td><a href="startQuiz?id=<%= id %>"><%= quiz_name %></a></td>
        <td><a href="ProfileServlet?username=<%= sender %>"><%= sender %></a></td>
        <td><%= score %></td>
        <td>
            <form action="DeleteChallenge" method="post">
                <input type="hidden" name="quiz_id" value="<%= id %>">
                <input type="hidden" name="score" value="<%= score %>">
                <input type="hidden" name="sender" value="<%= sender %>">
                <input type="hidden" name="quiz_name" value="<%= quiz_name %>">
                <input type="submit" value="Reject" class="reject-button">
            </form>
        </td>
        <td>
            <form action="AcceptChallenge" method="get">
                <input type="hidden" name="quiz_id" value="<%= id %>">
                <input type="hidden" name="score" value="<%= score %>">
                <input type="hidden" name="quiz_name" value="<%= quiz_name %>">
                <input type="hidden" name="sender" value="<%= sender %>">
                <input type="submit" value="Accept Challenge">
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } else { %>
<p class="no-challenges">You have no challenges at the moment.</p>
<% } %>

<a href="ProfileServlet" class="back-link">
    <img src="assets/backtohomepage.webp" alt="Back to Profile" />
    Back to My Profile
</a>
</body>
</html>



