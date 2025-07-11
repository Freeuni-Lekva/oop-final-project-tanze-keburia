<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String quizId = request.getParameter("quizID");
    String prompt = request.getParameter("prompt");
    String imageUrl = request.getParameter("imageUrl");
    String answer = request.getParameter("answer");
    String points = request.getParameter("points");

    if (quizId == null || quizId.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing quiz ID.");
        return;
    }

    if (prompt == null) prompt = "";
    if (imageUrl == null) imageUrl = "";
    if (answer == null) answer = "";
    if (points == null) points = "1";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Picture Response Question</title>
</head>
<body>

<h2>Create Picture Response Question</h2>

<form method="post" action="AddQuestion">
    <input type="hidden" name="quizID" value="<%= quizId %>" />
    <input type="hidden" name="type" value="PictureResponse" />

    <label for="prompt">Prompt (question text):</label><br>
    <input type="text" id="prompt" name="prompt" size="60" value="<%= prompt %>" required><br><br>

    <label for="imageUrl">Image URL:</label><br>
    <input type="text" id="imageUrl" name="imageUrl" size="60" value="<%= imageUrl %>" required><br><br>

    <label for="answer">Correct Answer:</label><br>
    <input type="text" id="answer" name="answer" size="40" value="<%= answer %>" required><br><br>

    <label for="points">Points:</label><br>
    <input type="number" id="points" name="points" value="<%= points %>" step="0.1" min="0" required><br><br>

    <input type="submit" value="Add Question">
</form>

<% if (!imageUrl.isEmpty()) { %>
<h3>Image Preview:</h3>
<img src="<%= imageUrl %>" alt="Question Image" width="300">
<% } %>

<br><br>
<a href="ConfigureQuiz?id=<%= quizId %>">Back to Quiz Configuration</a>

</body>
</html>
