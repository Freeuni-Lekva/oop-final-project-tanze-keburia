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
  int timeLimit = quiz.getTimeLimit();
%>

<html>
<head>
  <title>Take Quiz: <%= quiz.getName() %></title>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, sans-serif;
      background: #f4f6f8;
      margin: 0;
      padding: 20px;
    }

    .container {
      max-width: 800px;
      margin: auto;
      background: #fff;
      padding: 40px;
      border-radius: 12px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    }

    h1 {
      text-align: center;
      color: #333;
    }

    p {
      font-size: 16px;
      color: #444;
    }

    hr {
      border: none;
      border-top: 1px solid #ccc;
      margin: 30px 0;
    }

    .question-block {
      margin-bottom: 30px;
    }

    input[type="text"] {
      width: 100%;
      padding: 10px;
      margin-top: 8px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 15px;
    }

    button {
      padding: 10px 20px;
      font-size: 16px;
      background: #4CAF50;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      margin-top: 20px;
      transition: background 0.3s ease;
    }

    button:hover {
      background: #45a049;
    }

    a {
      text-decoration: none;
    }

    .back-link {
      display: inline-block;
      margin-top: 30px;
      font-size: 14px;
      color: #007BFF;
    }

    img {
      margin-top: 10px;
      border-radius: 6px;
    }
  </style>

  <% if (timeLimit > 0) { %>
  <script>
    let timeLeft = <%= timeLimit %>;
    window.onload = function () {
      const timerDisplay = document.createElement('p');
      timerDisplay.style.fontWeight = 'bold';
      timerDisplay.style.color = '#d9534f';
      document.body.insertBefore(timerDisplay, document.body.firstChild);

      function updateTimer() {
        if (timeLeft <= 0) {
          alert("Time is up! Submitting your quiz.");
          const form = document.forms[0];
          const hidden = document.createElement("input");
          hidden.type = "hidden";
          hidden.name = "submittedDueToTimeout";
          hidden.value = "true";
          form.appendChild(hidden);
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
<div class="container">
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
    <div class="question-block">
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
      <img src="<%= q.getStatement() %>" alt="Question image" width="300" />
      <input type="text" name="answer_<%= q.getID() %>" placeholder="Your answer here..." />
      <%
      } else {
      %>
      <p>Unsupported question type: <%= type %></p>
      <%
        }
      %>
    </div>
    <%
      }
    %>
    <button type="submit">End Quiz</button>
  </form>

  <br>
  <a class="back-link" href="viewAllQuizzes">← Back to Quiz List</a>
</div>
</body>
</html>




