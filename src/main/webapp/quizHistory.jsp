<%--
    Created by IntelliJ IDEA
    User: mzare
    Date: 06-Jul-25
    Time: 4:25 PM
    To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.quiz_result.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String currentUser = (String) session.getAttribute("username");
    if(currentUser == null){
        response.sendRedirect("login.jsp");
        return;
    }

    String targetUser = (String) request.getAttribute("targetUser");
    if(targetUser == null) {
        targetUser = currentUser;
    }

    List<QuizResult> quizHistory = (List<QuizResult>) request.getAttribute("History");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= targetUser %>'s Quiz History</title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="history-container">
    <div class="history-header">
        <a href="<%= currentUser.equals(targetUser) ? "Homepage" : "ProfileServlet?username=" + targetUser %>"
           class="btn btn-blue back-btn">Back</a>
        <h2><%= targetUser %>'s Quiz History</h2>
    </div>

    <div class="history-content">
        <% if(quizHistory.isEmpty()) { %>
        <p class="no-history">No quiz history available.</p>
        <% } else { %>
        <div class="quiz-history-list">
            <% for(QuizResult quizResult : quizHistory) {
                double score = quizResult.getScore();
                Timestamp time = quizResult.getSubmitTime();
                String quizId = quizResult.getQuizId();
                String quizName = quizResult.getQuizName();
            %>
            <div class="quiz-result-card">
                <h3><a href="startQuiz?id=<%=quizId%>" class="quiz-link"><%= quizName %></a></h3>
                <div class="quiz-meta">
                    <span class="score">Score: <%= score %>%</span>
                    <span class="timestamp">Completed: <%= time != null ? time.toString() : "Unknown" %></span>
                </div>
            </div>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>