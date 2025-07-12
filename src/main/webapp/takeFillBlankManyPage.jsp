<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.quiz_utilities.questions.Question" %>
<%@ page import="classes.quiz_utilities.answer.GeneralAnswer" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<%
    List<Question> questions = (List<Question>) session.getAttribute("questionList");
    int currentIndex = (Integer) session.getAttribute("currentIndex");
    Question currentQuestion = questions.get(currentIndex);
    Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");
    String currentAnswer = userAnswers != null && userAnswers.containsKey(currentQuestion.getID())
            ? userAnswers.get(currentQuestion.getID()).getAnswers().get(0)
            : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fill in the Blank - One Question</title>
    <link rel="stylesheet" href="dashboardStyle.css">
    <style>
        .card p {
            font-size: 25px;
        }

        .card input[type="text"] {
            font-size: 16px;
            padding: 10px 14px;
            width: 40%;
            min-width: 250px;
            max-width: 100%;
            border-radius: 6px;
            border: 1px solid #ccc;
        }
    </style>
</head>
<body>
<div class="dashboard full-height">
    <div class="quiz-center-container vertical-align">
        <div class="quiz-form">
            <form method="post">
                <div class="card mb-20">
                    <h3>Question <%= currentIndex + 1 %> of <%= questions.size() %></h3>

                    <%
                        String statement = currentQuestion.getStatement();
                        String renderedStatement = statement.replaceFirst("____",
                                "<input type='text' name='answer' value='" +
                                        (currentAnswer != null ? currentAnswer.replace("'", "&#39;") : "") + "' required/>");
                    %>
                    <p><%= renderedStatement %></p>
                </div>

                <div class="navigation">
                    <% if (currentIndex > 0) { %>
                    <button type="submit" formaction="PreviousQuestionServlet" class="btn btn-blue">Previous</button>
                    <% } %>

                    <% if (currentIndex < questions.size() - 1) { %>
                    <button type="submit" formaction="NextQuestionServlet" class="btn btn-blue">Next</button>
                    <% } else { %>
                    <button type="submit" formaction="NextQuestionServlet" class="btn btn-blue">Finish</button>
                    <% } %>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
