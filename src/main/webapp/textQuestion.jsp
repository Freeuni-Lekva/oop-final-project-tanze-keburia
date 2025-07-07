<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="classes.quiz_utilities.Question" %><%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="classes.quiz_utilities.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ServletContext context = application;
    String questionId = request.getParameter("id");
    String quizID = request.getParameter("quizID");
    QuestionDAO questions = (QuestionDAO) context.getAttribute("questions");

    Question question = questionId != null ? questions.getQuestion(questionId) : null;
    String description = question != null ? question.getStatement() : "";
    String answer = question != null ? question.getAnswer() : "";
    String points = question != null ? Double.toString(question.getPoints()) : "";
%>

<html>
<head>
    <title>Question</title>
</head>
<body>
<form action="SubmitTextQuestionServlet" method="post">
    <input type="hidden" name="quizID" value="<%= quizID %>">
    Statement: <input type="text" name="statement" value="<%= description %>" required><br>
    Answer: <input type="text" name="answer" value="<%= answer %>" required><br>
    Points: <input type="number" name="points" value="<%= points %>" step="0.1" min="0.1" required><br>
    <input type="submit" value="submit">
</form>

<form action="GoBackToQuiz" method="get">
    <input type="hidden" name="quizID" value="<%= quizID %>">
    <input type="submit" value="back">
</form>
</body>
</html>
