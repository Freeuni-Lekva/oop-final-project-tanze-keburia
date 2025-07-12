<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>

<%
  List<Question> questions = (List<Question>) session.getAttribute("questionList");
  int currentIndex = (Integer) session.getAttribute("currentIndex");
  Question currentQuestion = questions.get(currentIndex);
  Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
  String currentAnswer = "";
  if (userAnswers != null && userAnswers.containsKey(currentQuestion.getID())) {
    currentAnswer = userAnswers.get(currentQuestion.getID()).getAnswers().get(0);
  }

  // Split statement into prompt and image URL
  String[] parts = currentQuestion.getStatement().split(";;");
  String prompt = parts.length > 0 ? parts[0] : "";
  String imageUrl = parts.length > 1 ? parts[1] : "";
%>

<!DOCTYPE html>
<html>
<head>
  <title>Picture Response Question</title>
</head>
<body>
<h2><%= session.getAttribute("quiz") %></h2>

<form method="post">
  <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>
  <p><%= prompt %></p>

  <% if (!imageUrl.isEmpty()) { %>
  <img src="<%= imageUrl %>" alt="Question Image" style="max-width: 500px; margin: 10px 0;">
  <% } %>

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
</body>
</html>