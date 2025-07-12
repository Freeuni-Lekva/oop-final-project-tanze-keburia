<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 7/12/2025
  Time: 1:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, classes.quiz_utilities.questions.Question" %>
<%
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    String quizId = request.getParameter("quizID");
    if (quizId == null) quizId = (String) session.getAttribute("currentQuizID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fill-in-the-Blank Quiz</title>
    <link rel="stylesheet" href="dashboardStyle.css">
    <style>
        .card p {
            font-size: 25px;
        }
        .card input[type="text"] {
            font-size: 16px;
            padding: 10px 14px;
            width: 40%;
            min-width: 250px;
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="dashboard full-height">
    <div class="quiz-center-container vertical-align">
        <div class="quiz-form">
            <form action="SubmitQuizServlet" method="post">
                <input type="hidden" name="quizID" value="<%= quizId %>"/>

                <% for (Question q : questions) {
                    String inputName = "answer_" + q.getID();
                    String inputBox = "<input type='text' name='" + inputName + "' placeholder='Your answer here' required/>";
                    String statement = q.getStatement();
                    String rendered = statement.replaceFirst("____", inputBox);
                %>
                <div class="card mb-20">
                    <p><%= rendered %></p>
                </div>
                <% } %>

                <div class="submit-btn-container">
                    <button type="submit" class="btn btn-blue">Submit Quiz</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
