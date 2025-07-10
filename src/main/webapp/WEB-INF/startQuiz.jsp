<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>
<%@ page import="classes.quiz_result.QuizResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    int questionCount = ((Integer) request.getAttribute("questionCount")).intValue();
    List<QuizResult> leaderboard = (List<QuizResult>) request.getAttribute("leaderboard");
%>


<html>
<head>
    <title><%= quiz.getName() %></title>
</head>
<body>

<h2>Quiz: <%= quiz.getName() %></h2>

<p><strong>Type:</strong> <%= quiz.getType() %></p>
<p><strong>Number of Questions:</strong> <%= questionCount %></p>
        <%
    int timeLimit = quiz.getTimeLimit();
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

</p>
<p><strong>Topic:</strong> <%= quiz.getTopic() %></p>

<form action="StartActualQuizServlet" method="get">
    <input type="hidden" name="id" value="<%= quiz.getID() %>">
    <button type="submit">Start Quiz</button>
</form>

<h1>Leaderboard</h1>
<% if(leaderboard != null && !leaderboard.isEmpty()){%>
    <table border = "1" cellpadding = "8" cellspacing = "0">
        <thead>
            <tr>
                <th>Rank</th>
                <th>User</th>
                <th>Score</th>
                <th>Submit time</th>
            </tr>
        </thead>
        <tbody>
            <%
            int rank = 1;

                for (QuizResult entry : leaderboard) {
                    String user = entry.getUsername();
                    Double score = entry.getScore();
                    Timestamp time = entry.getSubmitTime();
            %>
                <tr>
                    <td><%=rank++%></td>
                    <td><a href = "ProfileServlet?username=<%=user%>"><%=user%></a></td>
                    <td><%=score%></td>
                    <td><%=time%></td>
                </tr>

            <%
            }  %>
        </tbody>
    </table>
    <%} else{%>
        <p>No participants yet</p>
    <%}
    %>

<br>
<a href="viewAllQuizzes">Back to All Quizzes</a>

</body>
</html>



