<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="classes.quiz_utilities.Question" %><%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page import="database.quiz_utilities.QuestionDAO" %>--%>
<%--<%@ page import="classes.quiz_utilities.Question" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%--<%--%>
<%--    ServletContext context = application;--%>
<%--    String questionId = request.getParameter("id");--%>
<%--    String quizID = request.getParameter("quizID");--%>
<%--    QuestionDAO questions = (QuestionDAO) context.getAttribute("questions");--%>

<%--    Question question = questionId != null ? questions.getQuestion(questionId) : null;--%>
<%--    String description = question != null ? question.getStatement() : "";--%>
<%--    String answer = question != null ? question.getAnswer() : "";--%>
<%--    String points = question != null ? Double.toString(question.getPoints()) : "";--%>
<%--%>--%>

<%--<html>--%>
<%--<head>--%>
<%--    <title>Question</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form action="SubmitTextQuestionServlet" method="post">--%>
<%--    <input type="hidden" name="quizID" value="<%= quizID %>">--%>
<%--    Statement: <input type="text" name="statement" value="<%= description %>" required><br>--%>
<%--    Answer: <input type="text" name="answer" value="<%= answer %>" required><br>--%>
<%--    Points: <input type="number" name="points" value="<%= points %>" step="0.1" min="0.1" required><br>--%>
<%--    <input type="submit" value="submit">--%>
<%--</form>--%>

<%--<form action="GoBackToQuiz" method="get">--%>
<%--    <input type="hidden" name="quizID" value="<%= quizID %>">--%>
<%--    <input type="submit" value="back">--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="classes.quiz_utilities.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ServletContext context = application;
    String questionId = request.getParameter("id");
    String quizID = request.getParameter("quizID");
    QuestionDAO questions = (QuestionDAO) context.getAttribute("questions");

    Question question = questionId != null ? questions.getQuestion(questionId) : null;
    String description = question != null ? question.getStatement() : "";
    String answer = question != null ? question.getAnswer() : "";
    String points = question != null ? Double.toString(question.getPoints()) : "";
%>

<html>
<head>
    <title>Add Question</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .card {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
            max-width: 500px;
            width: 100%;
        }

        h2 {
            margin-bottom: 24px;
            color: #2d3e50;
            font-size: 24px;
            font-weight: bold;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #333;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 16px;
        }

        .button-row {
            display: flex;
            justify-content: space-between;
        }

        input[type="submit"] {
            background-color: #28c76f;
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #22b364;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Add Question</h2>

    <form action="SubmitTextQuestionServlet" method="post">
        <input type="hidden" name="quizID" value="<%= quizID %>">

        <label for="statement">Statement:</label>
        <input type="text" id="statement" name="statement" value="<%= description %>" required>

        <label for="answer">Answer:</label>
        <input type="text" id="answer" name="answer" value="<%= answer %>" required>

        <label for="points">Points:</label>
        <input type="number" id="points" name="points" value="<%= points %>" step="0.1" min="0.1" required>

        <div class="button-row">
            <input type="submit" value="Submit">
        </div>
    </form>

    <form action="GoBackToQuiz" method="get">
        <input type="hidden" name="quizID" value="<%= quizID %>">
        <input type="submit" value="Back">
    </form>
</div>
</div>
</body>
</html>


