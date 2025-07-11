<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String questionId = request.getParameter("id");
    String quizID = request.getParameter("quizID");
    Question question = (Question) request.getAttribute("question");
    String description = question.getStatement();
    String answer = question.getAnswer();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Question</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Modify Question</h2>
    </div>

    <div class="center-form">
        <form action="ModifyQuestion" method="post">
            <input type="hidden" name="questionID" value="<%= questionId %>">
            <input type="hidden" name="quizID" value="<%= quizID %>">

            <label>Statement:</label>
            <input type="text" name="statement" value="<%= description %>" required>

            <label>Answer:</label>
            <input type="text" name="answer" value="<%= answer %>" required>

            <input type="submit" value="Submit" class="btn btn-blue">
        </form>
    </div>

    <div class="bottom-bar">
        <form action="GoBackToQuiz" method="get" style="margin-top: 10px;">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <button type="submit" class="btn btn-red">Back to Quiz</button>
        </form>
    </div>
</div>
</body>
</html>
