package classes.quiz_utilities;

import database.quiz_utilities.OptionsDAO;
import database.quiz_utilities.QuestionDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleChoiceChecker implements AnswerChecker {
    private OptionsDAO options;
    public MultipleChoiceChecker(OptionsDAO options) {
        this.options = options;
    }
    public double getPoints(String questionID, GeneralAnswer answer) throws SQLException {
        if(answer == null) return 0;
        if(!answer.getQuestionID().equals(questionID)) {
            return 0;
        }
        List<Option> questionChoices = options.getOptionsByQuestion(questionID);
        List<String> userChoices = answer.getAnswers();

        double res = 0;
        for (String userChoice : userChoices) {
            for (Option p : questionChoices) {
                if (p.getAnswer().trim().equals(userChoice.trim())) {
                    res += p.getPoints();
                }
            }
        }
        return res;
    }
}
