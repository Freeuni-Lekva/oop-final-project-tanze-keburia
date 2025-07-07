<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="classes.Question" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Integer index = (Integer) session.getAttribute("currentIndex");

    if (questions == null || index == null || index < 0 || index >= questions.size()) {
%>
<p>No more questions.</p>
<a href="viewAllQuizzes">Back to All Quizzes</a>
<%
        return;
    }

    Question currentQuestion = questions.get(index);
%>

<html>
<head>
    <title>Question <%= index + 1 %> of <%= questions.size() %></title>
</head>
<body>

<h2>Question <%= index + 1 %> of <%= questions.size() %></h2>
<p><%= currentQuestion.getStatement() %></p>

<!-- User Answer Input (not submitted yet) -->
<form method="post" action="#" style="margin-bottom: 10px;">
    <label for="userAnswer">Your Answer:</label><br/>
    <input type="text" id="userAnswer" name="userAnswer" style="width: 300px;" />
    <button type="submit" disabled>Save Answer</button> <%-- Placeholder --%>
</form>

<!-- Navigation Buttons -->
<form method="post">
    <% if (index > 0) { %>
    <button type="submit" formaction="previousQuestion">Previous</button>
    <% } %>

    <% if (index < questions.size() - 1) { %>
    <button type="submit" formaction="nextQuestion">Next</button>
    <% } else { %>
    <p>This was the last question.</p>
    <% } %>

    <button type="submit" formaction="endQuiz">End Quiz</button>
</form>

</body>
</html>



