<%--
    Created by IntelliJ IDEA
    User: mzare
    Date: 06-Jul-25
    Time: 4:25 PM
    To change this template use File | Settings | File Templates.
--%>

<%@ page import="classes.quiz_result.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="database.quiz_utilities.RealQuizDAO" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String currentUser = (String) session.getAttribute("username");
    if(currentUser == null){
        response.sendRedirect("login.jsp");
        return;
    }

    String targetUser = (String) request.getAttribute("targetUser");
    if(targetUser == null) {
        targetUser = currentUser;
    }

    List <QuizResult>quizHistory = (List<QuizResult>) request.getAttribute("History");
    //RealQuizDAO realQuizDAO = (RealQuizDAO) request.getAttribute("realQuizDAO");
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= targetUser %>'s Quiz History</title>
</head>
<body>
<a href="<%= currentUser.equals(targetUser) ? "Homepage" : "ProfileServlet?username=" + targetUser %>">Back</a>
<h2><%= targetUser %>'s Quiz History</h2>

<%
    if(quizHistory.isEmpty()){
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
%>
<p><a href="startQuiz?id=<%=quizId%>"><%= quizName %></a></p>
<p>Score: <%= score %>%</p>
<p>Completed: <%= time != null ? time.toString() : "Unknown" %></p>
<hr>
<%
        }
    }
%>
</body>
</html>