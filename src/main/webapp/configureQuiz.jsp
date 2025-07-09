<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ page import="classes.quiz_utilities.Question" %>--%>
<%--<%@ page import="classes.quiz_utilities.Quiz" %>--%>
<%--<%@ page import="database.quiz_utilities.QuestionDAO" %>--%>
<%--<%@ page import="database.quiz_utilities.QuizDAO" %>--%>
<%--<%@ page import="mapper.TypePageMapper" %>--%>
<%--<%@ page import="mapper.Topics" %>--%>
<%--<%@ page import="java.util.*" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.Question" %>
<%@ page import="classes.quiz_utilities.Quiz" %>
<%@ page import="database.quiz_utilities.QuestionDAO" %>
<%@ page import="database.quiz_utilities.QuizDAO" %>
<%@ page import="mapper.TypePageMapper" %>
<%@ page import="mapper.Topics" %>
<%@ page import="java.util.*" %>

<%
    String error = (String) session.getAttribute("errorMessage");
    ServletContext context = application;
    String quizID = request.getParameter("id");

    QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questions");
    QuizDAO quizzes = (QuizDAO) context.getAttribute("quizzes");

    List<Question> questions = questionDAO.getQuiz(quizID);
    Quiz quiz = quizzes.getQuiz(quizID);

    String quizName = quiz.getName();
    String questionPage = TypePageMapper.fromName(quiz.getType()).getJspPage();
    int timeLimit = quiz.getTimeLimit();

    String timeLimitMessage = timeLimit >= 1000000000
            ? "There is no current limit"
            : "Current time limit is " + timeLimit + " seconds";
%>

<html>
<head>
    <title>Configure Quiz</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f1f2f6;
            margin: 0;
            padding: 2rem;
            color: #2f3542;
        }

        .dashboard-container {
            max-width: 800px;
            margin: auto;
        }

        .section {
            background: #ffffff;
            border-radius: 12px;
            padding: 1.8rem 2.2rem;
            margin-bottom: 1.8rem;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
        }

        h1 {
            font-size: 2rem;
            margin-bottom: 1rem;
            color: #3742fa;
        }

        h2 {
            font-size: 1.4rem;
            margin-bottom: 1rem;
        }

        p {
            font-size: 1rem;
            margin-bottom: 1rem;
        }

        ul {
            padding-left: 1.2rem;
        }

        li {
            margin-bottom: 0.6rem;
        }

        a {
            color: #1e90ff;
            text-decoration: none;
            margin-right: 1rem;
        }

        form {
            margin-top: 1rem;
        }

        .inline-form {
            display: flex;
            align-items: center;
            gap: 0.8rem;
            flex-wrap: wrap;
        }

        input[type="text"], select {
            padding: 8px 10px;
            font-size: 0.95rem;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"], button {
            background-color: #2ed573;
            color: white;
            border: none;
            padding: 8px 16px;
            font-weight: bold;
            font-size: 0.95rem;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type="submit"]:hover, button:hover {
            background-color: #20bf6b;
        }

        .error-message {
            color: #e84118;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="dashboard-container">

    <% if (error != null) { %>
    <p class="error-message"><%= error %></p>
    <%
            session.removeAttribute("errorMessage");
        } %>

    <!-- Quiz Title & Questions Section -->
    <div class="section">
        <h1><%= quizName %></h1>
        <% if (questions.isEmpty()) { %>
        <p>No questions yet</p>
        <% } else { %>
        <ul>
            <% for (Question q : questions) {
                String questionURL = questionPage + "?id=" + q.getID() + "&quizID=" + quizID; %>
            <li>
                <a href="<%= questionURL %>">View</a>
                <form action="DeleteQuestion" method="post" style="display:inline;">
                    <input type="hidden" name="quizID" value="<%= quizID %>">
                    <input type="hidden" name="questionID" value="<%= q.getID() %>">
                    <input type="submit" value="Delete Question">
                </form>
            </li>
            <% } %>
        </ul>
        <% } %>

        <form action="AddQuestion" method="post">
            <input type="hidden" name="type" value="<%= quiz.getType() %>">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <input type="submit" value="Add Question">
        </form>
    </div>

    <!-- Time Limit Section -->
    <div class="section">
        <p><%= timeLimitMessage %></p>
        <form class="inline-form" action="SetTimeLimit" method="post">
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <label for="timeLimit"><strong>Time Limit (seconds):</strong></label>
            <input type="text" name="timeLimit" id="timeLimit" />
            <input type="submit" value="Set" />
        </form>
    </div>

    <!-- Publish Section -->
    <div class="section">
        <form class="inline-form" id="publishQuizForm" action="PublishQuiz" method="post">
            <label for="topicDropdown"><strong>Choose Topic:</strong></label>
            <select id="topicDropdown" name="topic">
                <% for (Topics t : Topics.values()) { %>
                <option value="<%= t.name() %>"><%= t.name().replace('_', ' ') %></option>
                <% } %>
            </select>
            <input type="hidden" name="quizID" value="<%= quizID %>">
            <button type="submit">Publish Quiz</button>
        </form>
    </div>

</div>
</body>
</html>

