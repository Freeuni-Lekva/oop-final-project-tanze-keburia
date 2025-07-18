package servlets.quiz_participation;


import classes.User;
import classes.mail.ChallengeMailSender;
import classes.achievement.AchievementAwarder;
import classes.quiz_result.QuizResult;
import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.checkers.TextAnswerChecker;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
import classes.social.Challenge;
import database.achievement.AchievementDAO;
import database.history.QuizHistoryDAO;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;
import database.social.ChallengeDAO;
import database.social.FriendsDAO;
import database.social.MailDAO;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/endQuiz")
public class EndQuizServlet extends HttpServlet {


    private double getBestScore(String username, QuizHistoryDAO history, String quizID) throws SQLException {
        double score = history.getMaxResultForUser(username, quizID);
        return score;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        String username = session.getAttribute("username").toString();
        Challenge challenge = (Challenge) session.getAttribute("challenge");
        if (quiz == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz not found in session.");
            return;
        }
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            MailDAO mailDAO = new MailDAO(conn);
            QuestionDAO questionDAO = new RealQuestionDAO(conn);
            QuizDAO quizDAO = new RealQuizDAO(conn);
            QuizHistoryDAO quizHist = new QuizHistoryDAO(conn, quizDAO);
            ChallengeDAO challenges = new ChallengeDAO(conn);
            FriendsDAO friendsDAO = new FriendsDAO(conn);
            if (questionDAO == null) {
                throw new ServletException("QuestionDAO not found in context.");
            }

            List<Question> questions = questionDAO.getQuiz(quiz.getID());
            TextAnswerChecker checker = new TextAnswerChecker(questionDAO);

            double totalScore = Double.parseDouble(request.getAttribute("totalScore").toString());

            String format = quiz.getPageFormat(); // assume this is "one-pager" or "one-at-a-time"
            ChallengeMailSender automaticSender = new ChallengeMailSender(mailDAO);
            automaticSender.sendMail(challenge, totalScore, new Timestamp(System.currentTimeMillis()));
            if(challenge != null){
                challenges.removeChallenge(challenge);
                challenge = null;
            }
            QuizResult quizResult = new QuizResult((String)session.getAttribute("username"), quiz.getID(), quiz.getName(), totalScore, new Timestamp(System.currentTimeMillis()));
            Quiz x =  quizDAO.getQuiz(quiz.getID());

            quizDAO.incrementPlayCount(quiz.getID());


            quizHist.addResult(quizResult);
            AchievementDAO achievementDAO = new AchievementDAO(conn);
            AchievementAwarder awarder = new AchievementAwarder(achievementDAO, quizDAO, quizHist);
            awarder.checkQuizParticipationAchievements(username, quiz.getID(), totalScore);
            double bestScore = getBestScore(username,quizHist, quiz.getID());
            request.setAttribute("bestScore", bestScore);
            List<String>friends = friendsDAO.getFriends(username);

            request.setAttribute("totalScore", totalScore);
            request.setAttribute("quiz", quiz);
            request.setAttribute("friends", friends);
            session.removeAttribute("quiz");
            request.getRequestDispatcher("endQuiz.jsp").forward(request, response);

        }catch(SQLException e) {
            throw new RuntimeException("could not connect database");
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}