<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>

<%
  List<Question> questions = (List<Question>) session.getAttribute("questions");
  Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Picture Response Quiz</title>
</head>
<body>
<h2><%= session.getAttribute("quiz") %></h2>

<form method="post" action="SubmitQuizServlet">
  <% for (int i = 0; i < questions.size(); i++) {
    Question question = questions.get(i);
    String currentAnswer = "";
    if (userAnswers != null && userAnswers.containsKey(question.getID())) {
      currentAnswer = userAnswers.get(question.getID()).getAnswers().get(0);
    }

    // Split statement into prompt and image URL
    String[] parts = question.getStatement().split(";;");
    String prompt = parts.length > 0 ? parts[0] : "";
    String imageUrl = parts.length > 1 ? parts[1] : "";
  %>
  <div>
    <h3>Question <%= i + 1 %></h3>
    <p><%= prompt %></p>

    <% if (!imageUrl.isEmpty()) { %>
    <img src="<%= imageUrl %>" alt="Question Image" style="max-width: 500px; margin: 10px 0;">
    <% } %>

    <textarea name="answer_<%= question.getID() %>" rows="4" style="width: 100%">
            <%= currentAnswer %>
        </textarea>
  </div>
  <% } %>

  <div>
    <button type="submit">Submit All Answers</button>
  </div>
</form>
</body>
</html>