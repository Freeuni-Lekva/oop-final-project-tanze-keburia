<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="mapper.TypePageMapper" %>
<%@ page import="mapper.Topics" %>
<%@ page import="java.util.*" %>
<%@ page import="classes.quiz_utilities.Question" %>
<%@ page import="classes.quiz_utilities.Quiz" %>

<%
    ServletContext context = application;
    String quizID = request.getParameter("id");

    QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
    QuizDAO quizzes = (QuizDAO) context.getAttribute("quizzes");

    List<Question> questions = questionDAO.getQuiz(quizID);
    Quiz quiz = quizzes.getQuiz(quizID);

    String quizName = quiz.getName();
    String questionPage = TypePageMapper.fromName(quiz.getType()).getJspPage();
    int timeLimit = quiz.getTimeLimit();

    String timeLimitMessage = timeLimit >= 1000000000
            ? "There is no current limit"
            : "Current time limit is " + timeLimit + " seconds";
%>

<html>
<head>
    <title>Configure Quiz</title>
</head>
<body>

<h1><%= quizName %></h1>

<% if (questions.isEmpty()) { %>
<p>No questions yet</p>
<% } else { %>
<ul>
    <% for (Question q : questions) {
        String questionURL = questionPage + "?id=" + q.getID() + "&quizID=" + quizID; %>
    <li>
        <a href="<%= questionURL %>">View</a>
        <form action="DeleteQuestion" method="post" style="display:inline;">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <input type="hidden" name="questionID" value="<%= q.getID() %>">
            <input type="submit" value="Delete Question">
        </form>
    </li>
    <% } %>
</ul>
<% } %>

<form action="AddQuestion" method="post" style="display:inline;">
    <input type="hidden" name="type" value="<%= quiz.getType() %>">
    <input type="hidden" name="quizID" value="<%= quizID %>">
    <input type="submit" value="Add question">
</form>

<p><%= timeLimitMessage %></p>

<form action="SetTimeLimit" method="post">
    <input type="hidden" name="quizID" value="<%= quizID %>">
    <label for="timeLimit">Time Limit (seconds):</label>
    <input type="text" name="timeLimit" id="timeLimit">
    <input type="submit" value="Set">
</form>

<!-- Topic selection and publish form -->
<form id="publishQuizForm" action="PublishQuiz" method="post">
    <label for="topicDropdown">Choose Topic:</label>
    <select id="topicDropdown" name="topic">
        <% for (Topics t : Topics.values()) { %>
        <option value="<%= t.name() %>"><%= t.name().replace('_', ' ') %></option>
        <% } %>
    </select>

    <input type="hidden" name="quizID" value="<%= quizID %>">
    <button type="submit">Publish Quiz</button>
</form>

</body>
</html>
