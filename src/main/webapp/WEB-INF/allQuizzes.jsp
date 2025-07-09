<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/4/2025
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.quiz_utilities.Quiz" %>
<html>
<head>
  <title>All Quizzes</title>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, sans-serif;
      background-color: #f4f6f8;
      margin: 0;
      padding: 20px;
    }

    .container {
      max-width: 800px;
      margin: auto;
      background: white;
      padding: 40px;
      border-radius: 12px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
    }

    h2 {
      text-align: center;
      color: #333;
      margin-bottom: 30px;
    }

    ul {
      list-style-type: none;
      padding: 0;
    }

    li {
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 15px;
      margin-bottom: 12px;
      transition: background-color 0.3s;
    }

    li:hover {
      background-color: #f1f1f1;
    }

    a {
      text-decoration: none;
      color: #007BFF;
      font-weight: bold;
    }

    .author {
      color: #666;
      font-size: 14px;
      margin-top: 4px;
      display: block;
    }

    .back-button {
      display: inline-block;
      margin-top: 30px;
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 8px;
      text-decoration: none;
      font-size: 15px;
      transition: background-color 0.3s ease;
    }

    .back-button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>

<div class="container">
  <h2>All Available Quizzes</h2>

  <%
    List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");

    if (quizzes == null || quizzes.isEmpty()) {
  %>
  <p>No quizzes found.</p>
  <%
  } else {
  %>
  <ul>
    <%
      for (Quiz quiz : quizzes) {
    %>
    <li>
      <a href="startQuiz?id=<%= quiz.getID() %>"><%= quiz.getName() %></a>
      <span class="author">Author: <%= quiz.getAuthor() %></span>
    </li>
    <%
      }
    %>
  </ul>
  <%
    }
  %>

  <a href="homepage.jsp" class="back-button">← Back to Homepage</a>
</div>

</body>
</html>
