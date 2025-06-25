<%@ page import="classes.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="database.QuestionDAO" %>
<%@ page import="classes.Quiz" %>
<%@ page import="database.QuizDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="mapper.TypePageMapper" %>
<%@ page import="mapper.Topics" %><%--
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
    String questionPage = TypePageMapper.fromName(quiz.getType()).getJspPage();
    int timeLimit = quiz.getTimeLimit();
    String timeLimitMessage = "";
    if(timeLimit >= 1000000000) {
        timeLimitMessage = "There is no current limit";
    }
    else {
        timeLimitMessage = "Current time limit is " + timeLimit + "seconds";
    }
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
<p>
    <%=timeLimitMessage%>
</p>
<form action = "SetTimeLimit" method="post" >
    <input type="hidden" name="quizID" value="<%=quizID%>">
    <label for="timeLimit">Time Limit (seconds):</label>
    <input type="text" name="timeLimit">
    <input type="submit" value = "set">
</form>
<label for="topic">Choose Topic:</label>
<select id="topicDropdown" name="topicSelect">
    <% for(Topics t : Topics.values()) { %>
    <option value="<%= t.name() %>"><%= t.name().replace('_', ' ') %></option>
    <% } %>
</select>

<!-- Your form with hidden input -->
<form action="PublishQuiz" method="post">
    <input type="hidden" name="topic" id="topicHiddenInput">
    <input type="hidden" name="quizID" value="<%= quizID %>">
    <button type="submit">Publish Quiz</button>
</form>

<script>
    document.getElementById('publishQuizForm').addEventListener('submit', function() {
        var selectedTopic = document.getElementById('topicDropdown').value;
        document.getElementById('topicHiddenInput').value = selectedTopic;
    });
</script>
</body>
</html>
