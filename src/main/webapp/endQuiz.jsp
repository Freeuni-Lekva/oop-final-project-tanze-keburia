<%--
  Created by IntelliJ IDEA.
  User: Ako
  Date: 7/7/2025
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Quiz Summary</title>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      background: #f0f2f5;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .summary-container {
      background: #fff;
      padding: 30px 40px;
      border-radius: 12px;
      box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
      text-align: center;
      width: 400px;
    }

    h2 {
      color: #333;
      margin-bottom: 20px;
    }

    p {
      font-size: 18px;
      color: #555;
      margin: 10px 0;
    }

    strong {
      color: #000;
    }

    button {
      margin-top: 20px;
      padding: 10px 20px;
      background: #4CAF50;
      color: white;
      border: none;
      border-radius: 8px;
      font-size: 16px;
      cursor: pointer;
      transition: background 0.3s ease;
    }

    button:hover {
      background: #45a049;
    }

    a {
      text-decoration: none;
    }
  </style>
</head>
<body>

<div class="summary-container">
  <h2>Quiz Completed</h2>

  <p><strong>Time Taken:</strong>
    <%= request.getAttribute("timeTaken") != null
            ? request.getAttribute("timeTaken")
            : "To be calculated" %>
  </p>

  <%
    Object scoreObj = request.getAttribute("totalScore");
    double score = (scoreObj instanceof Double) ? (Double) scoreObj : 0.0;
  %>
  <p><strong>Your Score:</strong> <%= score %></p>

  <a href="viewAllQuizzes"><button type="button">Back to Quiz List</button></a>
</div>

</body>
</html>
