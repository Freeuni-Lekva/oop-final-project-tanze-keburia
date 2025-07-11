package servlets.quiz_participation;


import classes.achievement.AchievementAwarder;
import classes.quiz_result.QuizResult;
import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.checkers.TextAnswerChecker;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
import database.achievement.AchievementDAO;
import database.history.QuizHistoryDAO;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@WebServlet("/endQuiz")
public class EndQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz not found in session.");
            return;
        }
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            QuizDAO quizDAO = new RealQuizDAO(conn);
            QuizHistoryDAO quizHist = new QuizHistoryDAO(conn, quizDAO);
            if (questionDAO == null) {
                throw new ServletException("QuestionDAO not found in context.");
            }

            List<Question> questions = questionDAO.getQuiz(quiz.getID());
            TextAnswerChecker checker = new TextAnswerChecker(questionDAO);

            double totalScore = 0;

            String format = quiz.getPageFormat(); // assume this is "one-pager" or "one-at-a-time"

            if ("All Questions on One Page".equalsIgnoreCase(format)) {
                for (Question q : questions) {
                    String paramName = "answer_" + q.getID();
                    String userInput = request.getParameter(paramName);

                    if (userInput != null && !userInput.trim().isEmpty()) {
                        GeneralAnswer userAnswer = new SingleAnswer(q.getID(), userInput.trim());
                        totalScore += checker.getPoints(q.getID(), userAnswer);
                    }
                }

            } else if ("One Question at a Time".equalsIgnoreCase(format)) {
                Map<String, GeneralAnswer> savedAnswers =
                        (Map<String, GeneralAnswer>) session.getAttribute("savedAnswers");

                if (savedAnswers != null) {
                    for (Question q : questions) {
                        GeneralAnswer answer = savedAnswers.get(q.getID());
                        if (answer != null) {
                            totalScore += checker.getPoints(q.getID(), answer);
                        }
                    }
                }
            }
            Quiz x =  quizDAO.getQuiz(quiz.getID());
            String username = (String) session.getAttribute("username");
            QuizResult quizResult = new QuizResult(username, x, totalScore, new Timestamp(System.currentTimeMillis()));
            quizHist.addResult(quizResult);
            System.out.println("fdaf;ljkad");
            AchievementDAO achievementDAO = new AchievementDAO(conn);
            AchievementAwarder awarder = new AchievementAwarder(achievementDAO, quizDAO, quizHist);
            awarder.checkQuizParticipationAchievements(username, quiz.getID(), totalScore);
            request.setAttribute("totalScore", totalScore);
            request.getRequestDispatcher("endQuiz.jsp").forward(request, response);

        }catch(SQLException e) {
            throw new RuntimeException("could not connect database");
        }

    }
}

