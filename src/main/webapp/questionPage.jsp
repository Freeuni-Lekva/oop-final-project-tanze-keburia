<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.*" %>
<%@ page import="java.util.List" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Integer index = (Integer) session.getAttribute("currentIndex");
    Map<String, GeneralAnswer> savedAnswers = (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");

    if (questions == null || index == null || index < 0 || index >= questions.size()) {
%>
<p>No more questions.</p>
<a href="viewAllQuizzes">Back to All Quizzes</a>
<%
        return;
    }

    Question currentQuestion = questions.get(index);
    String currentAnswer = "";
    if (savedAnswers != null && savedAnswers.containsKey(currentQuestion.getID())) {
        currentAnswer = savedAnswers.get(currentQuestion.getID()).getAnswers().get(0);
    }
%>

<html>
<head>
    <title>Question <%= index + 1 %> of <%= questions.size() %></title>
</head>
<body>

<h2>Question <%= index + 1 %> of <%= questions.size() %></h2>
<p><%= currentQuestion.getStatement() %></p>

<form method="post" action="saveAnswer">
    <input type="hidden" name="questionId" value="<%= currentQuestion.getID() %>" />
    <label for="userAnswer">Your Answer:</label><br/>
    <input type="text" id="userAnswer" name="userAnswer" value="<%= currentAnswer %>" style="width: 300px;" />
    <button type="submit" name="action" value="save">Save Answer</button>
    <button type="submit" name="action" value="customize">Customize Answer</button>
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




