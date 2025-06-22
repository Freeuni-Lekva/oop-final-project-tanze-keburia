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
    String questionPage = TypePageMapper.getPageForType(quiz.getType());
%>
<html>
<head>
    <title>Configure Quiz</title>
</head>
<body>
<h1><%= quiz.getName()%></h1>
<%
    if(questions.isEmpty()) {%>
    <p>No questions yet</p>
<%}
    else{%>
    <ul>
    <%
        for(Question q : questions) {
            %>
        <li>
            <a href="<%=questionPage%>?id=<%=q.getID()%>&quizID=<%=quizID%>">View
            </a>
        </li>
        <%}
        %>
        </ul>
<%}
%>
<form action = "AddQuestion" method="post">
    <input type="hidden" name="type" value="<%=quiz.getType()%>">
    <input type="hidden" name="quizID" value="<%=quiz.getID()%>">
    <input type="submit" value="Add question">
</form>
</body>
</html>
