<%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>
<form action = "StartMakingQuiz" method = "post">
   Enter Quiz Name: <input type="text", name = "quizName", required><br/>
    <label for="type">Choose type:</label>
    <select id="type", name = "type">
        <option value="Text">Text</option>
        <option value="MultipleChoice">Multiple-Choice</option>
        <option value="FillBlank">Fill in the Blank</option>
        <option value="PictureResponse">Picutre-response</option>
    </select><br/>
    <button type="submit">Create</button>
</form>
</body>
</html>
