package classes.quiz_utilities;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RealQuizChecker implements QuizChecker{
    private final AnswerChecker answerChecker;
    public RealQuizChecker(AnswerChecker answerChecker) {
        this.answerChecker = answerChecker;
    }
    public Map<String, Double> checkedAnswers(Map<String, GeneralAnswer> userAnswers) throws SQLException {
        Map<String, Double> results = new HashMap<>();
        for(String question : userAnswers.keySet()){
            results.put(question, answerChecker.getPoints(question, userAnswers.get(question)));
        }
        return results;
    }
}
