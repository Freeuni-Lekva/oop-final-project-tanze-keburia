<%@ page import="database.QuestionDAO" %>
<%@ page import="classes.Question" %><%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ServletContext context = application;
    String questionId = request.getParameter("id");
    System.out.println(questionId);
    String quizID = request.getParameter("quizID");
    QuestionDAO questions = (QuestionDAO)context.getAttribute("questions");
    System.out.println(questions.getAllQuestions().size());
    System.out.println(questionId);
    Question question = questions.getQuestion(questionId);
    String description = question.getStatement();
    String answer = question.getAnswer();
%>
<html>
<head>
    <title>Question</title>
</head>
<body>
<form action = 'ModifyQuestion' method = 'post'>
    <input type="hidden" name="questionID" value="<%=questionId%>">
    <input type="hidden" name="quizID" value="<%=quizID%>">
    Statement:<input type="text" name="statement" value="<%=description%>" required><br/>
    Answer:<input type="text" name ="answer" value = "<%=answer%>"required><br/>
    <input type="submit" value="submit">
</form>
<form action = "GoBackToQuiz" method = 'get'>
    <input type = "hidden" name = "quizID" value = "<%=quizID%>">
    <input type = "submit" value = "back">
</form>
</body>
</html>
