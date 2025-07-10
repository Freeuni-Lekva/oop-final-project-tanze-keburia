package classes.quiz_utilities.answer;

import java.util.ArrayList;
import java.util.List;

public class MultipleAnswer extends GeneralAnswer{
    List<String>answers;

    public MultipleAnswer(String questionID, List<String> answers) {
        super(questionID);
        this.answers = answers;
    }

    @Override
    public List<String> getAnswers() {
        return new ArrayList<>(answers);
    }
}
