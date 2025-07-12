<%--<%@ page import="classes.social.Challenge" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Your Challenges</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            background-color: #f0f8ff;--%>
<%--            padding: 40px;--%>
<%--            display: flex;--%>
<%--            flex-direction: column;--%>
<%--            align-items: center;--%>
<%--            min-height: 100vh;--%>
<%--            position: relative;--%>
<%--        }--%>

<%--        h1 {--%>
<%--            margin-bottom: 30px;--%>
<%--        }--%>

<%--        table {--%>
<%--            width: 90%;--%>
<%--            max-width: 900px;--%>
<%--            border-collapse: collapse;--%>
<%--            background-color: white;--%>
<%--            box-shadow: 0 0 10px rgba(0,0,0,0.1);--%>
<%--        }--%>

<%--        th, td {--%>
<%--            padding: 12px 15px;--%>
<%--            text-align: center;--%>
<%--            border-bottom: 1px solid #ddd;--%>
<%--        }--%>

<%--        th {--%>
<%--            background-color: #007bff;--%>
<%--            color: white;--%>
<%--        }--%>

<%--        tr:nth-child(even) {--%>
<%--            background-color: #f9f9f9;--%>
<%--        }--%>

<%--        tr:hover {--%>
<%--            background-color: #f1f1f1;--%>
<%--        }--%>

<%--        a {--%>
<%--            color: #007bff;--%>
<%--            text-decoration: none;--%>
<%--        }--%>

<%--        a:hover {--%>
<%--            text-decoration: underline;--%>
<%--        }--%>

<%--        form {--%>
<%--            display: inline;--%>
<%--        }--%>

<%--        input[type="submit"] {--%>
<%--            background-color: #007bff;--%>
<%--            border: none;--%>
<%--            padding: 8px 12px;--%>
<%--            color: white;--%>
<%--            cursor: pointer;--%>
<%--            border-radius: 4px;--%>
<%--            font-size: 14px;--%>
<%--            transition: background-color 0.2s;--%>
<%--        }--%>

<%--        input[type="submit"]:hover {--%>
<%--            background-color: #0056b3;--%>
<%--        }--%>

<%--        .reject-button {--%>
<%--            background-color: #dc3545;--%>
<%--        }--%>

<%--        .reject-button:hover {--%>
<%--            background-color: #c82333;--%>
<%--        }--%>

<%--        .no-challenges {--%>
<%--            font-size: 18px;--%>
<%--            color: #555;--%>
<%--            margin-top: 20px;--%>
<%--        }--%>

<%--        .challenge-icon {--%>
<%--            display: block;--%>
<%--            margin: 0 auto 30px auto;--%>
<%--            width: 100px;--%>
<%--            height: auto;--%>
<%--        }--%>

<%--        .back-link {--%>
<%--            display: flex;--%>
<%--            align-items: center;--%>
<%--            gap: 6px;--%>
<%--            position: absolute;--%>
<%--            bottom: 15px;--%>
<%--            left: 20px;--%>
<%--            text-decoration: none;--%>
<%--            font-size: 14px;--%>
<%--            font-weight: 500;--%>
<%--            color: #1a73e8;--%>
<%--        }--%>

<%--        .back-link:hover {--%>
<%--            color: #0d5bba;--%>
<%--        }--%>

<%--        .back-link img {--%>
<%--            width: 18px;--%>
<%--            height: 18px;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<%--%>
<%--    List<Challenge> challengeList = (List<Challenge>) request.getAttribute("challengeList");--%>
<%--%>--%>
<%--<h1>Your Challenges</h1>--%>

<%--<div style="text-align: center; margin-bottom: 30px;">--%>
<%--    <img src="assets/challenge.webp" alt="Challenge Icon" class="challenge-icon">--%>
<%--</div>--%>

<%--<% if (challengeList != null && !challengeList.isEmpty()) { %>--%>
<%--<table>--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th>Quiz</th>--%>
<%--        <th>Sender</th>--%>
<%--        <th>Score</th>--%>
<%--        <th>Reject</th>--%>
<%--        <th>Accept</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <%--%>
<%--        for (Challenge entry : challengeList) {--%>
<%--            Double score = entry.getScore();--%>
<%--            String sender = entry.getSender();--%>
<%--            String id = entry.getQuizID();--%>
<%--            String quiz_name = entry.getQuizName();--%>
<%--    %>--%>
<%--    <tr>--%>
<%--        <td><a href="startQuiz?id=<%= id %>"><%= quiz_name %></a></td>--%>
<%--        <td><a href="ProfileServlet?username=<%= sender %>"><%= sender %></a></td>--%>
<%--        <td><%= score %></td>--%>
<%--        <td>--%>
<%--            <form action="DeleteChallenge" method="post">--%>
<%--                <input type="hidden" name="quiz_id" value="<%= id %>">--%>
<%--                <input type="hidden" name="score" value="<%= score %>">--%>
<%--                <input type="hidden" name="sender" value="<%= sender %>">--%>
<%--                <input type="hidden" name="quiz_name" value="<%= quiz_name %>">--%>
<%--                <input type="submit" value="Reject" class="reject-button">--%>
<%--            </form>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--            <form action="AcceptChallenge" method="get">--%>
<%--                <input type="hidden" name="quiz_id" value="<%= id %>">--%>
<%--                <input type="hidden" name="score" value="<%= score %>">--%>
<%--                <input type="hidden" name="quiz_name" value="<%= quiz_name %>">--%>
<%--                <input type="hidden" name="sender" value="<%= sender %>">--%>
<%--                <input type="submit" value="Accept Challenge">--%>
<%--            </form>--%>
<%--        </td>--%>
<%--    </tr>--%>
<%--    <% } %>--%>
<%--    </tbody>--%>
<%--</table>--%>
<%--<% } else { %>--%>
<%--<p class="no-challenges">You have no challenges at the moment.</p>--%>
<%--<% } %>--%>

<%--<!-- Go back to My Profile link -->--%>
<%--<a href="ProfileServlet" class="back-link">--%>
<%--    <img src="assets/backtohomepage.webp" alt="Back to Profile" />--%>
<%--    Back to My Profile--%>
<%--</a>--%>

<%--</body>--%>
<%--</html>--%>

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



