<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    System.out.println(questions.size());
    int currentIndex = (Integer) session.getAttribute("currentIndex");
    Question currentQuestion = questions.get(currentIndex);
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
    Quiz quiz = (Quiz) session.getAttribute("quiz");
    System.out.println(quiz.getName());
    String currentAnswer = userAnswers.containsKey(currentQuestion.getID()) ?
            userAnswers.get(currentQuestion.getID()).getAnswers().get(0) : "";
%>

<form method="post">
    <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>
    <p><%= currentQuestion.getStatement() %></p>
    <textarea name="answer" rows="4" cols="50"><%= currentAnswer %></textarea>

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