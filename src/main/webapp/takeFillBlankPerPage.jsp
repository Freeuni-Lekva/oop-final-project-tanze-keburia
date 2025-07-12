<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 7/12/2025
  Time: 2:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="classes.quiz_utilities.answer.SingleAnswer" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Integer index = (Integer) session.getAttribute("currentIndex");
    if (index == null) index = 0;
    Question question = questions.get(index);
    int total = questions.size();

    Map<String, GeneralAnswer> savedAnswers = (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");
    String savedAnswer = "";
    if (savedAnswers != null && savedAnswers.containsKey(question.getID())) {
        savedAnswer = ((SingleAnswer) savedAnswers.get(question.getID())).getAnswer();
    }

%>
<html>
<head><title>Fill-in-the-Blank Question</title></head>
<body>
<h2>Question <%= index + 1 %> of <%= total %></h2>
<form method="post" action="saveAnswer">
    <input type="hidden" name="questionId" value="<%= question.getID() %>"/>
    <input type="hidden" name="currentIndex" value="<%= index %>"/>

    <%
        // Replace first blank with input field
        String statement = question.getStatement();
        String renderedStatement = statement.replaceFirst("____",
                "<input type='text' name='userAnswer' value='" + savedAnswer.replace("'", "&#39;") + "' required/>");
    %>
    <p><%= renderedStatement %></p>

    <br/><br/>
    <% if (index > 0) { %>
    <button type="submit" name="action" value="prev">Previous</button>
    <% } %>
    <% if (index < total - 1) { %>
    <button type="submit" name="action" value="next">Next</button>
    <% } else { %>
    <button type="submit" name="action" value="end">Submit Quiz</button>
    <% } %>
</form>


<%-- Save current answer into session's savedAnswers map --%>
<%
    String submittedAnswer = request.getParameter("userAnswer");
    String questionId = request.getParameter("questionId");

    if (submittedAnswer != null && questionId != null) {
        if (savedAnswers != null) {
            savedAnswers.put(questionId, new SingleAnswer(questionId, submittedAnswer.trim()));
            session.setAttribute("savedAnswers", savedAnswers); // re-save updated map
        }
    }
%>
</body>
</html>
