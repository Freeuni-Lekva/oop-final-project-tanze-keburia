<%--
  Created by IntelliJ IDEA.
  User: tarash-dolaberidze
  Date: 6/22/25
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

%>
<html>
<head>
    <title>Create Quiz</title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2>Create New Quiz</h2>
        <a href="MyProfileServlet" class="link link-blue">My Profile</a>
    </div>
    <form action="StartMakingQuiz" method="post" class="mt-20">
        <div class="mb-15">
            <label for="quizName"><strong>Enter Quiz Name:</strong></label><br>
            <input type="text" id="quizName" name="quizName" class="input-full" required>
        </div>

        <div class="mb-20">
            <label for="type"><strong>Choose Type:</strong></label><br>
            <select id="type" name="type" class="input-full">
                <option value="Text">Text</option>
                <option value="MultipleChoice">Multiple-Choice</option>
                <option value="FillBlank">Fill in the Blank</option>
                <option value="PictureResponse">Picture-response</option>
            </select>
            <div class = "mb-20">
                <label for="format">Choose page format:</label>
                <select id="format" name="format" required>
                    <option value="All Questions on One Page">All Questions on One Page</option>
                    <option value="One Question at a Time">One Question at a Time</option>
                </select>
            </div>
        </div>


        <button type="submit" class="btn btn-green">Create</button>
    </form>

    <div class="bottom-bar mt-30">
        <a href="Homepage" class="link link-blue">Back to Home</a>
    </div>
</div>
</body>
</html>
