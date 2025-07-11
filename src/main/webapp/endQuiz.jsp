<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/7/2025
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%><%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Quiz Summary</title>
</head>
<body>

<h2>Quiz Completed</h2>

<p><strong>Time Taken:</strong> To be calculated</p>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);
  Object scoreObj = request.getAttribute("totalScore");
  double score = (scoreObj instanceof Double) ? (Double) scoreObj : 0.0;
  double bestScore = (Double)request.getAttribute("bestScore");
  String quizId = ((Quiz)request.getAttribute("quiz")).getID();
  String quizName = ((Quiz)request.getAttribute("quiz")).getName();
  List<String> friends = (List<String>)request.getAttribute("friends");
  String username = (String)session.getAttribute("username");
  System.out.println(bestScore);
%>
<p><strong>Your Score:</strong> <%= score %></p>
<ul>
  <%
    for (String friend : friends) {
      String friendUrl = "Profile?username=" + friend;
  %>
  <li>
    <a href="<%= friendUrl %>"><%= friend %></a>
    <form onsubmit="event.preventDefault(); sendChallenge(this, '<%=friend%>');">
      <input type = "hidden" name = "score" value ="<%=bestScore%>">
      <input type = "hidden" name = "quizID" value= "<%=quizId%>">
      <input type = "hidden" name = "receiver" value = "<%=friend%>">
      <input type = "hidden" name = "sender" value = "<%=username%>">
      <input type = "hidden" name = "quizName" value = "<%=quizName%>">

      <input type = "submit" value = "Challenge">
    </form>
  </li>
  <%}
    %>.
</ul>

<a href="viewAllQuizzes"><button type="button">Back to Quiz List</button></a>
<script>
  function sendChallenge(form, friend) {

    const formData = new URLSearchParams();
    formData.append('score', form.querySelector('[name="score"]').value);
    formData.append('quizID', form.querySelector('[name="quizID"]').value);
    formData.append('receiver', form.querySelector('[name="receiver"]').value);
    formData.append('quizName', form.querySelector('[name="quizName"]').value);

    fetch('ChallengeServlet', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: formData
    })
            .then(response => {
              if (!response.ok) throw new Error('Server error: ' + response.status);
              return response.text();
            })
            .then(result => {
              alert(result);
              // Update button to show success
              const button = form.querySelector('input[type="submit"]');
              button.value = "Challenged!";
              button.disabled = true;
            })
            .catch(error => {
              alert('Error: ' + error.message);
            });
  }
</script>
</body>
</html>


