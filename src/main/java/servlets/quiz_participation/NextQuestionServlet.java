package servlets.quiz_participation;

import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.MultipleAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.checkers.AnswerChecker;
import classes.quiz_utilities.checkers.QuizChecker;
import classes.quiz_utilities.checkers.RealQuizChecker;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@WebServlet("/NextQuestionServlet")
public class NextQuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            // Get current state
            List<Question> questions = (List<Question>) session.getAttribute("questionList");
            int currentIndex = (Integer) session.getAttribute("currentIndex");
            Map<String, GeneralAnswer> userAnswers = (Map<String, GeneralAnswer>) session.getAttribute("userAnswers");

            // Store current answer
            String questionId = questions.get(currentIndex).getID();
            String answerText = request.getParameter("answer");
            userAnswers.put(questionId, new SingleAnswer(questionId, answerText));
            Quiz cur = (Quiz)session.getAttribute("quiz");
            if(cur.getType().equals("MultipleChoice")) {
                List<String>ans = new ArrayList<>(); ans.add(answerText);
                userAnswers.put(questionId, new MultipleAnswer(questionId, ans));
            }
            // Move to next question or finish
            if (currentIndex < questions.size() - 1) {
                session.setAttribute("currentIndex", currentIndex + 1);
                if(cur.getType().equals("MultipleChoice")) {request.getRequestDispatcher("manyMultipleChoice.jsp").forward(request, response);}
                if(cur.getType().equals("Text")) request.getRequestDispatcher("manyTextQuestions.jsp").forward(request, response);
                if(cur.getType().equals("FillBlank")) request.getRequestDispatcher("takeFillBlankManyPage.jsp").forward(request, response);
                if(cur.getType().equals("PictureResponse")) request.getRequestDispatcher("pictureResponseMany.jsp").forward(request, response);
            } else {
                // Use QuizChecker to evaluate all answers
                AnswerChecker answerChecker = (AnswerChecker) session.getAttribute("answerChecker");
                answerChecker.setDAO(conn);
                System.out.println(answerChecker);
                QuizChecker quizChecker = new RealQuizChecker(answerChecker);
                Map<String, Double> questionScores = quizChecker.checkedAnswers(userAnswers);

                // Calculate total score
                double totalScore = 0;
                for (Double score : questionScores.values()) {
                    totalScore += score;
                }


                request.setAttribute("totalScore", totalScore);


                // Forward to results page
                request.getRequestDispatcher("endQuiz").forward(request, response);

                // Clean up session
                session.removeAttribute("questionList");
                session.removeAttribute("currentIndex");
                session.removeAttribute("userAnswers");
            }
        } catch (SQLException e) {
            throw new ServletException("Error calculating quiz results", e);
        }
    }
}