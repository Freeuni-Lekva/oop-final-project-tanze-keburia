package servlets.quiz_participation;



import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.checkers.AnswerChecker;
import classes.quiz_utilities.checkers.MultipleChoiceChecker;
import classes.quiz_utilities.checkers.TextAnswerChecker;
import classes.quiz_utilities.options.Option;
import classes.quiz_utilities.questions.MultipleChoiceQuestion;
import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/StartActualQuizServlet")
public class StartActualQuizServlet extends HttpServlet {

    private List<Question> questions = new ArrayList<>();
    private Quiz  quiz;
    private QuestionDAO questionDAO;
    private OptionsDAO optionsDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizID = request.getParameter("id");
        HttpSession session = request.getSession();
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            QuizDAO quizDAO = new RealQuizDAO(conn);
            this.questionDAO = new RealQuestionDAO(conn);
            this.optionsDAO = new OptionsDAO(conn);
            this.quiz = quizDAO.getQuiz(quizID);
           questions = questionDAO.getQuiz(quizID);

            if (quiz == null || questions == null || questions.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found or has no questions.");
                return;
            }

            session.setAttribute("questionList", questions);
            session.setAttribute("currentIndex", 0);
            session.setAttribute("currentQuizID", quizID);
            session.setAttribute("quiz", quiz);

            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", questions);

            String format = quiz.getPageFormat();


            if(quiz.getType().equals("Text")){
                if("One Question at a Time".equals(format)){
                    textManyPages(request, response);
                } else{
                    textOnePage(request, response);
                }
            }
            if(quiz.getType().equals("FillBlank")){
                if("One Question at a Time".equalsIgnoreCase(format)){
                    fillBlankManyPages(request,response);
                } else {
                    fillBlankOnePage(request,response);
                }
            }
            if(quiz.getType().equals("MultipleChoice")){
                if("One Question at a Time".equalsIgnoreCase(format)){
                    fillMultipleChoicesMany(request, response);
                }
                else{
                    fillMultipleChoicesOne(request, response);
                }
            }
            System.out.println(quiz.getType());
            if(quiz.getType().equals("PictureResponse")){
                if("One Question at a Time".equalsIgnoreCase(format)){
                    fillPictureResponseMany(request, response);
                }
                else {
                    fillPictureResponseOne(request, response);
                }
            }
        }catch(SQLException e) {
            throw new RuntimeException("Could not connect database");
        }
    }

    private void fillPictureResponseOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("questions", questions);
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("pictureResponseOne.jsp").forward(request, response);
    }

    private void fillPictureResponseMany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("currentQuestion", questions.get(0));
        session.setAttribute("questionIndex", 0);
        session.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("pictureResponseMany.jsp").forward(request, response);
    }

    private List<MultipleChoiceQuestion> buildChoiceQuestions(List<Question> questions) throws SQLException {
        List<MultipleChoiceQuestion> questions1 = new ArrayList<>();
        for(Question question : questions){
            MultipleChoiceQuestion x = new MultipleChoiceQuestion(question);
            List<Option> options = optionsDAO.getOptionsByQuestion(question.getID());
            x.setOptions(options);
            questions1.add(x);
        }
        return questions1;
    }

    private void fillMultipleChoicesOne(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        List<MultipleChoiceQuestion>questions1 = buildChoiceQuestions(questions);
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("questions", questions1);
        session.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new MultipleChoiceChecker(optionsDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("oneMultipleChoice.jsp").forward(request, response);
    }

    private void fillMultipleChoicesMany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        List<MultipleChoiceQuestion> questions1 = buildChoiceQuestions(questions);
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("currentQuestion", questions.get(0));
        session.setAttribute("questionIndex", 0);
        session.setAttribute("questions", questions1);
        session.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new MultipleChoiceChecker(optionsDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("manyMultipleChoice.jsp").forward(request, response);
    }


    private void textManyPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("currentQuestion", questions.get(0));
        session.setAttribute("questionIndex", 0);
        session.setAttribute("questions", questions);
        session.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("manyTextQuestions.jsp").forward(request, response);

    }


    private void textOnePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("questions", questions);
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("oneTextQuestions.jsp").forward(request, response);
    }
    private void fillBlankManyPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("currentQuestion", questions.get(0));
        session.setAttribute("questionIndex", 0);
        session.setAttribute("questions", questions);
        session.setAttribute("totalQuestions", questions.size());
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("takeFillBlankManyPage.jsp").forward(request, response);
    }
    private void fillBlankOnePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, GeneralAnswer> userAnswers = new HashMap<>();
        HttpSession session = request.getSession();
        session.setAttribute("userAnswers", userAnswers);
        session.setAttribute("questions", questions);
        AnswerChecker answerChecker = new TextAnswerChecker(questionDAO);
        session.setAttribute("answerChecker", answerChecker);
        request.getRequestDispatcher("takeFillBlankOnePage.jsp").forward(request, response);
    }
}

