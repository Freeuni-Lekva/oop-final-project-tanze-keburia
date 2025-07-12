<%@ page import="classes.quiz_utilities.questions.MultipleChoiceQuestion" %>
<%@ page import="classes.quiz_utilities.options.Option" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>

<%
    List<MultipleChoiceQuestion> questions = (List<MultipleChoiceQuestion>) session.getAttribute("questions");
    int currentIndex = (Integer) session.getAttribute("currentIndex");
    MultipleChoiceQuestion currentQuestion = questions.get(currentIndex);
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
    String currentAnswer = "";
    if (userAnswers != null && userAnswers.containsKey(currentQuestion.getID())) {
        currentAnswer = userAnswers.get(currentQuestion.getID()).getAnswers().get(0);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Multiple Choice Question</title>
</head>
<body>
<h2><%= session.getAttribute("quiz") %></h2>

<form method="post">
    <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>
    <p><%= currentQuestion.getStatement() %></p>

    <% for (Option option : currentQuestion.getOptions()) { %>
    <div>
        <input type="radio"
               name="answer"
               value="<%= option.getAnswer() %>"
            <%= option.getAnswer().equals(currentAnswer) ? "checked" : "" %>>
        <label><%= option.getAnswer() %></label>
    </div>
    <% } %>

    <div class="navigation">
        <% if (currentIndex > 0) { %>
        <button type="submit" formaction="PreviousQuestionServlet">Previous</button>
        <% } %>

        <% if (currentIndex < questions.size() - 1) { %>
        <button type="submit" formaction="NextQuestionServlet">Next</button>
        <% } else { %>
        <button type="submit" formaction="NextQuestionServlet">Finish</button>
        <% } %>
    </div>
</form>
</body>
</html>