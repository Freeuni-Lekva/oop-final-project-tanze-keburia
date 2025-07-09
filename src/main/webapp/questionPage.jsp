<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/5/2025
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.*" %>
<%@ page import="java.util.List" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Integer index = (Integer) session.getAttribute("currentIndex");
    Map<String, GeneralAnswer> savedAnswers = (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");
    Quiz quiz = (Quiz) session.getAttribute("quiz");

    if (questions == null || index == null || index < 0 || index >= questions.size()) {
%>
<p>No more questions.</p>
<a href="viewAllQuizzes">Back to All Quizzes</a>
<%
        return;
    }

    Question currentQuestion = questions.get(index);
    String currentAnswer = "";
    if (savedAnswers != null && savedAnswers.containsKey(currentQuestion.getID())) {
        currentAnswer = savedAnswers.get(currentQuestion.getID()).getAnswers().get(0);
    }
%>

<html>
<head>
    <title>Question <%= index + 1 %> of <%= questions.size() %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background: #f2f2f2;
        }

        h2 {
            color: #2e3b4e;
            margin-bottom: 15px;
        }

        .question-box {
            background: #ffffff;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        input[type="text"] {
            padding: 10px;
            width: 80%;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-top: 8px;
            margin-bottom: 16px;
        }

        button {
            padding: 10px 18px;
            margin-right: 10px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
        }

        button:hover {
            background-color: #45a049;
        }

        .nav-buttons {
            margin-top: 10px;
        }

        .timer {
            font-weight: bold;
            margin-bottom: 20px;
            color: #e53935;
        }
    </style>

    <% int timeLimit = quiz.getTimeLimit(); %>
    <% if (timeLimit > 0) { %>
    <script>
        let totalTime = <%= timeLimit %>;

        if (!sessionStorage.getItem("quizTimer")) {
            sessionStorage.setItem("quizTimer", totalTime);
        }

        let timeLeft = parseInt(sessionStorage.getItem("quizTimer"));
        window.onload = function () {
            const timerDisplay = document.createElement('p');
            timerDisplay.className = "timer";
            timerDisplay.innerText = "Time Left: " + timeLeft + " seconds";
            document.body.insertBefore(timerDisplay, document.body.firstChild);

            function updateTimer() {
                if (timeLeft <= 0) {
                    sessionStorage.removeItem("quizTimer");
                    alert("Time is up! Submitting your quiz.");

                    const form = document.createElement("form");
                    form.method = "POST";
                    form.action = "endQuiz";

                    const timeoutInput = document.createElement("input");
                    timeoutInput.type = "hidden";
                    timeoutInput.name = "submittedDueToTimeout";
                    timeoutInput.value = "true";
                    form.appendChild(timeoutInput);

                    document.body.appendChild(form);
                    form.submit();
                } else {
                    timerDisplay.innerText = "Time Left: " + timeLeft + " seconds";
                    timeLeft--;
                    sessionStorage.setItem("quizTimer", timeLeft);
                    setTimeout(updateTimer, 1000);
                }
            }

            updateTimer();
        };
    </script>
    <% } %>
</head>

<body>
<div class="question-box">
    <h2>Question <%= index + 1 %> of <%= questions.size() %></h2>
    <p><%= currentQuestion.getStatement() %></p>

    <form method="post" action="saveAnswer">
        <input type="hidden" name="questionId" value="<%= currentQuestion.getID() %>" />
        <label for="userAnswer">Your Answer:</label><br/>
        <input type="text" id="userAnswer" name="userAnswer" value="<%= currentAnswer %>" />
        <br/>
        <button type="submit" name="action" value="save">Save Answer</button>
        <button type="submit" name="action" value="customize">Customize Answer</button>
    </form>
</div>

<div class="nav-buttons">
    <form method="post">
        <% if (index > 0) { %>
        <button type="submit" formaction="previousQuestion">Previous</button>
        <% } %>

        <% if (index < questions.size() - 1) { %>
        <button type="submit" formaction="nextQuestion">Next</button>
        <% } else { %>
        <p>This was the last question.</p>
        <% } %>

        <button type="submit" formaction="endQuiz">End Quiz</button>
    </form>
</div>
</body>
</html>





