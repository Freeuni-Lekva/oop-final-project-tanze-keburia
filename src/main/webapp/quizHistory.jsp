<%@ page import="classes.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="database.RealQuizDAO" %>
<%@ page import="java.sql.Timestamp" %><%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 06-Jul-25
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = (String) session.getAttribute("username");
  //  RealQuizDAO quizDAO = (RealQuizDAO) application.getAttribute("quizDAO");
    if(username == null){
        response.sendRedirect("login.jsp");
        return;
    }
    List<QuizResult> quizzes = (List<QuizResult>) request.getAttribute("History");
%>

<html>
<head>
    <title><%= username %>'s Quiz History</title>
</head>
<body>
    <h2><%= username %>'s Quiz History</h2>

    <%
        if(quizzes == null || quizzes.isEmpty()){%>
         <p> No quiz History available.</p>
    <% } else{ %>
        <%
            for(int i = 0; i < quizzes.size(); i++){
                QuizResult quizResult = quizzes.get(i);
                int score = quizResult.getScore();
                Timestamp time = quizResult.getSubmitTime();
                String quizId = quizResult.getQuizId();
                String quizName = quizDAO.getQuizNameById(quizId);
                %>
                        <p><%= score %></p>
                        <p><%= time%></p>
                       <p><a href="createQuiz.jsp"> <%= quizName %> </a></p>
                <%
            }
        %>
    <%}%>
</body>
</html>



