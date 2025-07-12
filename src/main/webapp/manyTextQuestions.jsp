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

    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    int currentIndex = (Integer) session.getAttribute("currentIndex");
    Question currentQuestion = questions.get(currentIndex);
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
    Quiz quiz = (Quiz) session.getAttribute("quiz");

    String currentAnswer = userAnswers.containsKey(currentQuestion.getID())
            ? userAnswers.get(currentQuestion.getID()).getAnswers().get(0)
            : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= quiz.getName() %></title>
    <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
<div class="dashboard full-height">
    <div class="header-row">
        <h2><%= quiz.getName() %></h2>
    </div>

    <div class="quiz-center-container">
        <form method="post" class="quiz-form">
            <div class="card mb-20">
                <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>
                <p><%= currentQuestion.getStatement() %></p>
                <textarea name="answer" rows="4" class="text-area no-resize"><%= currentAnswer %></textarea>
            </div>

            <div class="navigation">
                <% if (currentIndex > 0) { %>
                <button type="submit" formaction="PreviousQuestionServlet" class="btn btn-blue nav-button">Previous</button>
                <% } %>

                <% if (currentIndex < questions.size() - 1) { %>
                <button type="submit" formaction="NextQuestionServlet" class="btn btn-blue nav-button">Next</button>
                <% } else { %>
                <button type="submit" formaction="NextQuestionServlet" class="btn btn-green nav-button">Finish</button>
                <% } %>
            </div>
        </form>
    </div>
</div>
</body>
</html>
