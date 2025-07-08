<%--
    Created by IntelliJ IDEA
    User: mzare
    Date: 06-Jul-25
    Time: 4:25 PM
    To change this template use File | Settings | File Templates.
--%>
<%@ page import="classes.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="database.RealQuizDAO" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String currentUser = (String) session.getAttribute("username");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String targetUser = (String) request.getAttribute("targetUser");
    if (targetUser == null) targetUser = currentUser;

    List<QuizResult> quizHistory = (List<QuizResult>) request.getAttribute("quizHistory");
    RealQuizDAO realQuizDAO = (RealQuizDAO) application.getAttribute("realQuizDAO");
%>

<html>
<head>
    <title><%= targetUser %>'s Quiz History</title>
</head>
<body>

<a href="<%= currentUser.equals(targetUser) ? "homepage.jsp" : "ProfileServlet?username=" + targetUser %>">Back</a>
<h2><%= targetUser %>'s Quiz History</h2>

<%
    if (quizHistory == null || quizHistory.isEmpty()) {
%>
<p>No quiz history available.</p>
<%
} else {
    for (QuizResult quizResult : quizHistory) {
        String quizId = quizResult.getQuizId();
        String quizName = realQuizDAO != null ? realQuizDAO.getQuizNameById(quizId) : null;
        if (quizName == null || quizName.trim().isEmpty()) {
            quizName = "Quiz #" + quizId;
        }
%>
<div>
    <p><strong>Quiz:</strong> <a href="QuizTakeServlet?quizId=<%= quizId %>"><%= quizName %></a></p>
    <p><strong>Score:</strong> <%= quizResult.getScore() %>%</p>
    <p><strong>Completed:</strong> <%= quizResult.getSubmitTime() != null ? quizResult.getSubmitTime().toString() : "Unknown" %></p>
    <hr>
</div>
<%
        }
    }
%>

</body>
</html>

