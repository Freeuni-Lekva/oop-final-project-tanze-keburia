<%@ page import="classes.quiz_result.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String currentUser = (String) session.getAttribute("username");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String targetUser = (String) request.getAttribute("targetUser");
    if (targetUser == null) {
        targetUser = currentUser;
    }

    List<QuizResult> quizHistory = (List<QuizResult>) request.getAttribute("History");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= targetUser %>'s Quiz History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            padding: 30px;
            display: flex;
            justify-content: center;
        }

        .container {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
            text-align: center;
        }


        .history-header {
            align-items: center;
            margin-bottom: 30px;
        }

        .history-header img {
            width: 50px;
            height: 50px;
            display: block;
            margin: 0 auto;
        }

        .quiz-item {
            background-color: #fff;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .quiz-item a {
            font-size: 18px;
            font-weight: bold;
            color: #007bff;
            text-decoration: none;
        }

        .quiz-item a:hover {
            text-decoration: underline;
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            margin-top: 40px;
            font-size: 16px;
            text-decoration: none;
            color: #007bff;
        }

        .back-link img {
            width: 24px;
            height: 24px;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="history-header">
        <img src="assets/history.webp" alt="History Icon">
        <h2><%= targetUser %>'s Quiz History</h2>
    </div>

    <%
        if (quizHistory.isEmpty()) {
    %>
    <p>No quiz history available.</p>
    <%
    } else {
        for (QuizResult quizResult : quizHistory) {
            double score = quizResult.getScore();
            Timestamp time = quizResult.getSubmitTime();
            String quizId = quizResult.getQuizId();
            String quizName = quizResult.getQuizName();
    %>
    <div class="quiz-item">
        <p><a href="startQuiz?id=<%= quizId %>"><%= quizName %></a></p>
        <p>Score: <%= score %>%</p>
        <p>Completed: <%= time != null ? time.toString() : "Unknown" %></p>
    </div>
    <%
            }
        }
    %>

    <!-- Back to Homepage Link -->
    <a href="Homepage" class="back-link">
        <img src="assets/backtohomepage.webp" alt="Back to Home" />
        Back to Homepage
    </a>
</div>

</body>
</html>
