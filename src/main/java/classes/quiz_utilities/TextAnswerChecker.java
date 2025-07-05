package classes.quiz_utilities;

import database.quiz_utilities.QuestionDAO;

import java.util.ArrayList;

public class TextAnswerChecker implements AnswerChecker {
    private QuestionDAO questions;

    public TextAnswerChecker(QuestionDAO questions){
        this.questions = questions;
    }
    public double getPoints(String questionID, GeneralAnswer userAnswer) {
        ArrayList<String> userAns = new ArrayList<>(userAnswer.getAnswers());
        if(userAns.size() != 1) {throw new RuntimeException("Exactly 1 answer is allowed");}
        Question q = questions.getQuestion(questionID);
        String answer = q.getAnswer();
        if(answer.equals(userAns.get(0))) return q.getPoints();
        return 0;
    }
}
