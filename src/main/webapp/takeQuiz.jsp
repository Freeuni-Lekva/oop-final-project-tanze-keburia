<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.*" %>
<%@ page import="mapper.TypePageMapper" %>

<%
  Quiz quiz = (Quiz) request.getAttribute("quiz");
  List<Question> questions = (List<Question>) request.getAttribute("questions");
%>

<html>
<head>
  <title>Take Quiz: <%= quiz.getName() %></title>
  <%-- Countdown Timer Script --%>
  <% int timeLimit = quiz.getTimeLimit(); %>
  <% if (timeLimit > 0) { %>
  <script>
    let timeLeft = <%= timeLimit %>;
    window.onload = function () {
      const timerDisplay = document.createElement('p');
      timerDisplay.style.fontWeight = 'bold';
      document.body.insertBefore(timerDisplay, document.body.firstChild);

      function updateTimer() {
        if (timeLeft <= 0) {
          // alert("Time is up! Submitting your quiz.");
          // document.forms[0].submit();
          alert("Time is up! Submitting your quiz.");

          const form = document.forms[0];
          const hidden = document.createElement("input");
          hidden.type = "hidden";
          hidden.name = "submittedDueToTimeout";
          hidden.value = "true";
          form.appendChild(hidden);

          alert("Time is up! Submitting your quiz.");
          form.submit();


        } else {
          timerDisplay.innerText = "Time Left: " + timeLeft + " seconds";
          timeLeft--;
          setTimeout(updateTimer, 1000);
        }
      }
      updateTimer();
    };
  </script>
  <% } %>
</head>
<body>

<h1>Take Quiz: <%= quiz.getName() %></h1>
<p><strong>Topic:</strong> <%= quiz.getTopic() %></p>
<%
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
    <p><%= q.getAnswer() == null || q.getAnswer().isEmpty() ? "Write what is shown in the picture:" : q.getAnswer() %></p>
    <img src="<%= q.getStatement() %>" alt="Question image" width="300" /><br>
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
<a href="viewAllQuizzes">Back to Quiz List</a>

</body>
</html>



