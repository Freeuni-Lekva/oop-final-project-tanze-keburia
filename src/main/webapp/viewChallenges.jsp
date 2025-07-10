<%@ page import="classes.social.Challenge" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 7/10/25
  Time: 10:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your challenges</title>
</head>
<body>
      <%
        List<Challenge>challengeList = (List<Challenge>)request.getAttribute("challengeList");
      %>
      <h1>Your Challenges</h1>
      <% if(challengeList != null && !challengeList.isEmpty()){%>
      <table border = "1" cellpadding = "8" cellspacing = "0">
        <thead>
        <tr>
          <th>Quiz</th>
          <th>Sender</th>
          <th>Score</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <%
          for (Challenge entry : challengeList) {
           // String receiver = entry.getReceiver();
            Double score = entry.getScore();
            String sender = entry.getSender();
            String id = entry.getQuizID();
            String quiz_name = entry.getQuizName();
        %>
        <tr>
          <td><a href="startQuiz?id=<%=id%>"><%=quiz_name%></a></td>
          <td><a href = "ProfileServlet?username=<%=sender%>"><%=sender%></a></td>
          <td><%=score%></td>
          <td>
            <form action = "DeleteChallenge" method = "post">
              <input type = "hidden" name="quiz_id" value="<%=id%>">
              <input type = "hidden" name="score" value="<%=score%>">
              <input type = "hidden" name = "sender" value ="<%=sender%>">
              <input type = "hidden" name = "quiz_name" value="<%=quiz_name%>" >
              <input type = "submit" value ="Reject Challenge(PUSSY)">
            </form>
          </td>
        </tr>

        <%
          }  %>
        </tbody>
      </table>
      <%} else{%>
      <p>No Challenges yet</p>
      <%}
      %>

</body>
</html>
