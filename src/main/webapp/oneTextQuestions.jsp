<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Complete Quiz</title>
</head>
<body>
<h2><%= session.getAttribute("quiz") %></h2>

<form method="post" action="SubmitQuizServlet">
    <% for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        String currentAnswer = "";
        if (userAnswers.containsKey(question.getID())) {
            currentAnswer = userAnswers.get(question.getID()).getAnswers().get(0);
        }
    %>
    <div class="question">
        <h3>Question <%= i + 1 %></h3>
        <p><%= question.getStatement() %></p>
        <textarea name="answer_<%= question.getID() %>"
                  rows="4" cols="50"><%= currentAnswer %></textarea>
    </div>
    <% } %>

    <div class="submit">
        <button type="submit">Submit All Answers</button>
    </div>
</form>
</body>
</html>