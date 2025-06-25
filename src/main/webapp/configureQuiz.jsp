<%@ page import="classes.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="database.QuestionDAO" %>
<%@ page import="classes.Quiz" %>
<%@ page import="database.QuizDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="mapper.TypePageMapper" %><%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    ServletContext context = application;
    String quizID = request.getParameter("id");
    QuestionDAO questionDAO = (QuestionDAO)context.getAttribute("questions");
    QuizDAO quizzes = (QuizDAO)context.getAttribute("quizzes");
    List<Question>questions = questionDAO.getQuiz(quizID);
    Quiz quiz = quizzes.getQuiz(quizID);
    String quizName=quiz.getName();
    String questionPage = TypePageMapper.getPageForType(quiz.getType());
%>
<html>
<head>
    <title>Configure Quiz</title>
</head>
<body>
<h1><%= quizName%></h1>
<%
    if(questions.isEmpty()) {%>
    <p>No questions yet</p>
<%}
    else{%>
    <ul>
    <%
        for(Question q : questions) {
            String questionURL = questionPage + "?id=" + q.getID() + "&quizID=" + quizID;
            %>
        <li>
            <a href="<%=questionURL%>">View
            </a>
            <form action = "DeleteQuestion" method = "post" style="display:inline;">
                <input type = "hidden" name ="quizID" value ="<%=quizID%>">
                <input type="hidden" name="questionID" value ="<%=q.getID()%>">
                <input type="submit" value = "Delete Question">
            </form>
        </li>
        <%}
        %>
        </ul>
<%}
%>
<form action = "AddQuestion" method="post" style="display:inline;">
    <input type="hidden" name="type" value="<%=quiz.getType()%>">
    <input type="hidden" name="quizID" value="<%=quizID%>">
    <input type="submit" value="Add question">
</form>
<form action = "SetTimeLimit" method="post" >
    <input type="hidden" name="quizID" value="<%=quizID%>">
    <label for="timeLimit">Time Limit (minutes):</label>
    <input type="text" name="timeLimit">
    <input type="submit" value = "set">
</form>
</body>
</html>
