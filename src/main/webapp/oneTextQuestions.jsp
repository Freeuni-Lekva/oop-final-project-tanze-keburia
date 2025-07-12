<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>
<%@ page import="classes.quiz_utilities.quiz.Quiz" %>

<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Quiz quiz = (Quiz) session.getAttribute("quiz");
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Complete Quiz</title>
    <link rel="stylesheet" href="dashboardStyle.css">
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }

        .quiz-center-container {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .quiz-form {
            width: 100%;
            max-width: 800px;
        }

        .text-area {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border-radius: 6px;
            border: 1px solid #ccc;
            resize: none;
            margin-top: 10px;
            box-sizing: border-box;
        }

        .submit-btn-container {
            text-align: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <div class="header-row">
        <h2><%= quiz.getName() %></h2>
    </div>

    <div class="quiz-center-container">
        <form method="post" action="SubmitQuizServlet" class="quiz-form">
            <% for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                String currentAnswer = "";
                if (userAnswers.containsKey(question.getID())) {
                    currentAnswer = userAnswers.get(question.getID()).getAnswers().get(0);
                }
            %>
            <div class="card mb-20">
                <h3>Question <%= i + 1 %></h3>
                <p><%= question.getStatement() %></p>
                <textarea name="answer_<%= question.getID() %>" rows="4" class="text-area"><%= currentAnswer %></textarea>
            </div>
            <% } %>

            <div class="submit-btn-container">
                <button type="submit" class="btn btn-blue">Submit All Answers</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
