<%--
  Created by IntelliJ IDEA.
  User: GUGA
  Date: 6/27/2025
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String username = (String) session.getAttribute("username");
  if (username == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<html>
<head><title>Compose Mail</title></head>
<body>
<h2>Compose Message</h2>
<form method="post" action="SendMail">
  To: <input type="text" name="receiver" required /><br/>
  Subject: <input type="text" name="subject" required /><br/>
  Message:<br/>
  <textarea name="content" rows="6" cols="50" required></textarea><br/>
  <input type="submit" value="Send" />
</form>
<a href="Homepage">Back to Home</a>
</body>
</html>
