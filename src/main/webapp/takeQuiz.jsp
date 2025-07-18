<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="mapper.TypePageMapper" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<%@ page import="database.admin.Admins" %>

<%
  Quiz quiz = (Quiz) request.getAttribute("quiz");
  System.out.println(quiz.getName());
  List<Question> questions = (List<Question>) request.getAttribute("questions");

  String username = (String) session.getAttribute("username");
  boolean isAdmin = username != null && Admins.isAdmin(username);
%>

<html>
<head>
  <title>Take Quiz: <%= quiz.getName() %></title>
</head>
<body>

<h1>Take Quiz: <%= quiz.getName() %></h1>
<p><strong>Topic:</strong> <%= quiz.getTopic() %></p>
<%
  int timeLimit = quiz.getTimeLimit();
  String timeDisplay;
  if (timeLimit == 0) {
    timeDisplay = "No limit";
  } else if (timeLimit < 60) {
    timeDisplay = timeLimit + " seconds";
  } else if (timeLimit % 60 == 0) {
    timeDisplay = (timeLimit / 60) + " minutes";
  } else {
    timeDisplay = (timeLimit / 60) + " minutes and " + (timeLimit % 60) + " seconds";
  }
%>
<p><strong>Time Limit:</strong> <%= timeDisplay %></p>
<hr>

<form method="post" action="endQuiz">
  <%
    int number = 1;
    for (Question q : questions) {
      String type = TypePageMapper.fromName(quiz.getType()).getTypeName();
  %>
  <div style="margin-bottom: 20px;">
    <p><strong>Question <%= number++ %>:</strong></p>
    <%
      if ("Text".equalsIgnoreCase(type) || "FillBlank".equalsIgnoreCase(type)) {
    %>
    <p><%= q.getStatement() %></p>
    <input type="text" name="answer_<%= q.getID() %>" placeholder="Your answer here..." />
    <%
    } else if ("PictureResponse".equalsIgnoreCase(type)) {
    %>
    <%
      // Extract prompt and image from statement
      String[] parts = q.getStatement().split(";;");
      String prompt = parts.length > 0 ? parts[0] : "";
      String image = parts.length > 1 ? parts[1] : "";
    %>
    <p><%= prompt %></p>
    <% if (!image.isEmpty()) { %>
    <img src="<%= image %>" alt="Question image" width="300" /><br>
    <% } %>
    <input type="text" name="answer_<%= q.getID() %>" placeholder="Your answer here..." />
    <%
    } else {
    %>
    <p>Unsupported question type: <%= type %></p>
    <%
      }
    %>
  </div>
  <hr>
  <%
    }
  %>

  <button type="submit">End Quiz</button>
</form>

<br>
<% if (isAdmin) { %>
<a href="AdminQuizzesServlet">Back to Quiz List</a>
<% } else { %>
<a href="viewAllQuizzes">Back to Quiz List</a>
<% } %>

</body>
</html>