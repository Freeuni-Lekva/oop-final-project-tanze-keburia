<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String questionId = request.getParameter("id");
    String quizID = request.getParameter("quizID");
    Question question = (Question)request.getAttribute("question");
    String description = question != null ? question.getStatement() : "";
    String answer = question != null ? question.getAnswer() : "";
%>
<html>
<head>
    <title>Question</title>
    <link rel="stylesheet" type="text/css" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2 class="link-blue">Edit Question</h2>
        <a href="ConfigureQuiz?id=<%=quizID%>" class="btn btn-blue">Back to Quiz</a>
    </div>

    <form action='ModifyQuestion' method='post' class="card">
        <input type="hidden" name="questionID" value="<%=questionId%>">
        <input type="hidden" name="quizID" value="<%=quizID%>">

        <div class="input-group mb-20">
            <label class="link-blue">Statement:</label>
            <input type="text" name="statement" value="<%=description%>" class="input-full" required>
        </div>

        <div class="input-group mb-20">
            <label class="link-blue">Answer:</label>
            <input type="text" name="answer" value="<%=answer%>" class="input-full" required>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-blue">Save Changes</button>
            <a href="ConfigureQuiz?id=<%=quizID%>" class="btn btn-blue">Back to Quiz</a>
        </div>
    </form>
</div>
</body>
</html>