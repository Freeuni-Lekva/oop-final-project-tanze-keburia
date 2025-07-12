<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    int currentIndex = (Integer) session.getAttribute("currentIndex");
    Question currentQuestion = questions.get(currentIndex);
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
    String currentAnswer = userAnswers != null && userAnswers.containsKey(currentQuestion.getID())
            ? userAnswers.get(currentQuestion.getID()).getAnswers().get(0)
            : "";
%>

<form method="post">
    <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>

    <%
        String statement = currentQuestion.getStatement();
        String renderedStatement = statement.replaceFirst("____",
                "<input type='text' name='answer' value='" +
                        (currentAnswer != null ? currentAnswer.replace("'", "&#39;") : "") + "' required/>");
    %>
    <p><%= renderedStatement %></p>

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