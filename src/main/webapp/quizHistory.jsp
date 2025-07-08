<%--
    Created by IntelliJ IDEA
    User: mzare
    Date: 06-Jul-25
    Time: 4:25 PM
    To change this template use File | Settings | File Templates.
--%>

<%@ page import="classes.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="database.quiz_utilities.RealQuizDAO" %>
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

    List quizHistory = (List) request.getAttribute("History");
    //RealQuizDAO realQuizDAO = (RealQuizDAO) request.getAttribute("realQuizDAO");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= targetUser %>'s Quiz History</title>
</head>
<body>
<a href="<%= currentUser.equals(targetUser) ? "Homepage" : "profile.jsp?username=" + targetUser %>">Back</a>
<h2><%= targetUser %>'s Quiz History</h2>

<%
    if(quizHistory == null || quizHistory.isEmpty()){
%>
<p>No quiz history available.</p>
<%
} else {
    for(Object obj : quizHistory){
        QuizResult quizResult = (QuizResult) obj;
        double score = quizResult.getScore();
        Timestamp time = quizResult.getSubmitTime();
        String quizId = quizResult.getQuizId();
        String quizName = "";
        quizName = quizResult.getQuizName();

        if(quizName == null || quizName.isEmpty()) {
            quizName = "Quiz #" + quizId;
        }
%>
<p><a href="QuizTakeServlet?quizId=<%= quizId %>"><%= quizName %></a></p>
<p>Score: <%= score %>%</p>
<p>Completed: <%= time != null ? time.toString() : "Unknown" %></p>
<hr>
<%
        }
    }
%>
</body>
</html>