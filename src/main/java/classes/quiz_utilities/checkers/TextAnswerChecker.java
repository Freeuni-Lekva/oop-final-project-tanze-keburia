package classes.quiz_utilities.checkers;

import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.questions.Question;
import database.quiz_utilities.QuestionDAO;

import java.util.ArrayList;

public class TextAnswerChecker implements AnswerChecker {
    private QuestionDAO questions;

    public TextAnswerChecker(QuestionDAO questions){
        this.questions = questions;
    }

    @Override
    public double getPoints(String questionID, GeneralAnswer userAnswer) {
//        ArrayList<String> userAns = new ArrayList<>(userAnswer.getAnswers());
//        if(userAns.size() != 1) {throw new RuntimeException("Exactly 1 answer is allowed");}
//        Question q = questions.getQuestion(questionID);
//        String answer = q.getAnswer();
//        if(answer.equals(userAns.get(0))) return q.getPoints();
//        return 0;
        if (userAnswer == null || userAnswer.getAnswers().isEmpty()) {
            return 0.0;
        }

        ArrayList<String> userAns = new ArrayList<>(userAnswer.getAnswers());
        if (userAns.size() != 1) {
            throw new RuntimeException("Exactly 1 answer is allowed");
        }

        Question q = questions.getQuestion(questionID);
        if (q == null) {
            return 0.0;
        }

        String correctAnswer = q.getAnswer().trim();
        String userSubmitted = userAns.get(0).trim();

        if (correctAnswer.equals(userSubmitted)) {
            return q.getPoints();
        }

        return 0.0;
    }
}
