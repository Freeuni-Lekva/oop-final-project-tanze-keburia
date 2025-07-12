<%@ page import="classes.quiz_utilities.questions.MultipleChoiceQuestion" %>
<%@ page import="classes.quiz_utilities.options.Option" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>

<%
    List<MultipleChoiceQuestion> questions = (List<MultipleChoiceQuestion>) session.getAttribute("questions");
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Multiple Choice Quiz</title>
</head>
<body>
<h2><%= session.getAttribute("quiz") %></h2>

<form method="post" action="SubmitQuizServlet">
    <% for (int i = 0; i < questions.size(); i++) {
        MultipleChoiceQuestion question = questions.get(i);
        String currentAnswer = "";
        if (userAnswers != null && userAnswers.containsKey(question.getID())) {
            currentAnswer = userAnswers.get(question.getID()).getAnswers().get(0);
        }
    %>
    <div>
        <h3>Question <%= i + 1 %></h3>
        <p><%= question.getStatement() %></p>

        <% for (Option option : question.getOptions()) { %>
        <div>
            <input type="radio"
                   name="answer_<%= question.getID() %>"
                   value="<%= option.getAnswer() %>"
                <%= option.getAnswer().equals(currentAnswer) ? "checked" : "" %>>
            <label><%= option.getAnswer() %></label>
        </div>
        <% } %>
    </div>
    <% } %>

    <div>
        <button type="submit">Submit All Answers</button>
    </div>
</form>
</body>
</html>