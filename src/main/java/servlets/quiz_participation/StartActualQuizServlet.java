package servlets.quiz_participation;



import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.checkers.AnswerChecker;
import classes.quiz_utilities.checkers.TextAnswerChecker;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuestionDAO;
import database.quiz_utilities.RealQuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/StartActualQuizServlet")
public class StartActualQuizServlet extends HttpServlet {
    private List<Question> questions;
    private QuestionDAO questionDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("id");

        ServletContext context = getServletContext();
        HttpSession session = request.getSession();
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            this.questionDAO = new RealQuestionDAO(conn);

            Quiz quiz = quizDAO.getQuiz(quizID);
            this.questions = questionDAO.getQuiz(quizID);

            if (quiz == null || questions == null || questions.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found or has no questions.");
                return;
            }

            session.setAttribute("questionList", questions);
            session.setAttribute("currentIndex", 0);
            session.setAttribute("currentQuizID", quizID);
            session.setAttribute("quiz", quiz);
            session.setAttribute("savedAnswers", new HashMap<String, GeneralAnswer>());

            String format = quiz.getPageFormat();
            if(quiz.getType().equals("FillBlank")){
                if("One Question at a Time".equalsIgnoreCase(format)){
                    fillBlankManyPages(request,response);
                } else {
                    fillBlankOnePage(request,response);
                }
            }

//            if ("One Question at a Time".equalsIgnoreCase(format)) {
//                request.setAttribute("currentQuestion", questions.get(0));
//                request.setAttribute("questionIndex", 0);
//                request.setAttribute("totalQuestions", questions.size());
//                request.getRequestDispatcher("questionPage.jsp").forward(request, response);
//            } else {
//                request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
//            }
        }catch(SQLException e) {
            throw new RuntimeException("Could not connect database");
        }
    }
    private void fillBlankManyPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        List<Question> questions = (List<Question>) request.getSession().getAttribute("questionList");
        request.setAttribute("userAnswer", userAnswers);
        request.setAttribute("question", questions.get(0));
        request.setAttribute("currentIndex", 0);
        request.setAttribute("questions", questions);
        request.setAttribute("totalQuestions", questions.size()); // This should now work
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        request.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("takeFillBlankPerPage.jsp").forward(request, response);
    }
    private void fillBlankOnePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        request.setAttribute("userAnswer", userAnswers);
        request.setAttribute("questions", questions);
        request.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        request.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("takeFillBlankOnePage.jsp").forward(request, response);
    }
}
