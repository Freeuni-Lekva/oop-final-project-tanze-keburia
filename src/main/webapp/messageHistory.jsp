<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="classes.Mail" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mzare
  Date: 06-Jul-25
  Time: 11:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    String currentUser = (String) request.getAttribute("currentUser");
    String otherUser = (String) request.getAttribute("otherUser");
    List<Mail> conversation = (List<Mail>) request.getAttribute("conversation");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
<head>
    <title>Message History with <%= otherUser %></title>
</head>
<body>
    <h2>Message History with <%= otherUser %></h2>
    <%
        if(conversation == null || conversation.isEmpty()){
            %>
            <p>No messages found with <%= otherUser %>.</p>
        <%}  else {  %>
    <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
        <% for (Mail mail : conversation) { %>
        <div style="margin-bottom: 15px; padding: 10px; background-color:
            <%= mail.getSender().equals(currentUser) ? "#e6f3ff" : "#f0f0f0" %>;">
            <p><strong>From:</strong> <%= mail.getSender() %></p>
            <p><strong>To:</strong> <%= mail.getReceiver() %></p>
            <p><strong>Subject:</strong> <%= mail.getSubject() %></p>
            <p><strong>Content:</strong> <%= mail.getContent() %></p>
            <p><strong>Sent:</strong> <%= dateFormat.format(mail.getTimestamp()) %></p>
        </div>
        <% } %>
    </div>
    <% }%>
</body>
</html>
