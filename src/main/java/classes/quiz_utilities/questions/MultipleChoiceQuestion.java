package classes.quiz_utilities.questions;

import classes.quiz_utilities.checkers.MultipleChoiceChecker;
import classes.quiz_utilities.options.Option;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion extends RealQuestion{
    List<Option> options = new ArrayList<Option>();
    public MultipleChoiceQuestion(Question question) {

        super(question.getStatement(),
                question.getAnswer(),
                question.getQuizID(),
                question.getID(),
                String.valueOf(question.getPoints()));
        options = new ArrayList<>();
    }
    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
