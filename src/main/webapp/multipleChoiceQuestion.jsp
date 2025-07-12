<%@ page import="classes.quiz_utilities.options.Option" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Question</title>
</head>
<body>
<%
  Question question = (Question) request.getAttribute("question");
  List<Option> options = (List<Option>) request.getAttribute("options");
%>

<h1>Edit Question</h1>

<!-- Main Question Form -->
<form action="/ModifyQuestion" method="post">
  <input type="hidden" name="quizID" value="<%= question.getQuizID() %>">
  <input type="hidden" name="questionID" value="<%= question.getID() %>">

  <div>
    <label>Question Statement:</label><br>
    <textarea name="statement" rows="4" cols="50" required><%= question.getStatement() %></textarea>
  </div>

  <div>
    <input type="submit" value="Save Question">
  </div>
</form>

<div>
  <h2>Current Options:</h2>
  <% if (options != null && !options.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Option Text</th>
      <th>Points</th>
      <th>Action</th>
    </tr>
    <% for (Option option : options) { %>
    <tr>
      <td><%= option.getAnswer() %></td>
      <td><%= option.getPoints() %></td>
      <td>
        <form action="DeleteOption" method="post" style="display:inline;">
          <input type="hidden" name="quizID" value="<%= question.getQuizID() %>">
          <input type="hidden" name="questionID" value="<%= question.getID() %>">
          <input type="hidden" name="optionID" value="<%= option.getOptionID() %>">
          <input type="submit" value="Delete" onclick="return confirm('Delete this option?');">
        </form>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No options available</p>
  <% } %>
</div>

<!-- Add Option Form - Outside the main form -->
<div>
  <h2>Add New Option:</h2>
  <form action="AddOption" method="post">
    <input type="hidden" name="quizID" value="<%= question.getQuizID() %>">
    <input type="hidden" name="questionID" value="<%= question.getID() %>">

    <label>Option Text:
      <input type="text" name="optionText" required>
    </label>

    <label>Points:
      <input type="number" name="points" step="0.1" required>
    </label>

    <input type="submit" value="Add Option">
  </form>
</div>

</body>
</html>