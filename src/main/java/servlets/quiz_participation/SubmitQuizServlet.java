package servlets.quiz_participation;

import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.checkers.AnswerChecker;
import classes.quiz_utilities.checkers.QuizChecker;
import classes.quiz_utilities.checkers.RealQuizChecker;
import classes.quiz_utilities.questions.Question;
import database.database_connection.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try (Connection conn = DatabaseConnector.getInstance().getConnection()){
            // 1. Get required components from session
            QuizChecker quizChecker = (QuizChecker) session.getAttribute("quizChecker");
            List<Question> questions = (List<Question>) session.getAttribute("questionList");

            // 2. Collect all user answers
            Map<String, GeneralAnswer> userAnswers = new HashMap<>();
            for (Question question : questions) {
                String answer = request.getParameter("answer_" + question.getID());
                if (answer != null) {
                    GeneralAnswer generalAnswer = new SingleAnswer(question.getID(), answer);
                    userAnswers.put(question.getID(), generalAnswer);
                }
            }

            AnswerChecker answerChecker = (AnswerChecker) session.getAttribute("answerChecker");
            answerChecker.setDAO(conn);
            quizChecker = new RealQuizChecker(answerChecker);
            // 3. Use QuizChecker to validate all answers
            Map<String, Double> results = quizChecker.checkedAnswers(userAnswers);

            // 4. Calculate total score
            double totalScore = 0;
            for (Double score : results.values()) {
                totalScore += score;
            }

            // 5. Prepare results for display
            request.setAttribute("results", results);
            request.setAttribute("totalScore", totalScore);
            request.setAttribute("questions", questions);
            request.setAttribute("userAnswers", userAnswers);

            // 6. Forward to results page
            request.getRequestDispatcher("endQuiz").forward(request, response);
            session.removeAttribute("answerChecker");
            session.removeAttribute("questionList");
//            session.removeAttribute("cu
        } catch (SQLException e) {
            throw new ServletException("Error checking answers", e);
        }
    }
}