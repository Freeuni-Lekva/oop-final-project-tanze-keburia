<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 7/12/2025
  Time: 1:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, classes.quiz_utilities.questions.Question" %>
<%
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    String quizId = request.getParameter("quizID");
    if (quizId == null) quizId = (String) session.getAttribute("currentQuizID");
%>
<html>
<head><title>Fill-in-the-Blank Quiz</title></head>
<body>
<h2>Fill in the Blanks (One Page Format)</h2>
<form action="endQuiz" method="post">
    <input type="hidden" name="quizID" value="<%= quizId %>"/>

    <% for (Question q : questions) { %>
    <div>
        <p><%= q.getStatement() %></p>
        <input type="text" name="answer_<%= q.getID() %>" placeholder="Your answer here" required/>
    </div>
    <hr/>
    <% } %>

    <button type="submit">Submit Quiz</button>
</form>
</body>
</html>

