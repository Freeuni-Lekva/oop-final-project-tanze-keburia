package classes.quiz_utilities.answer;

import java.util.Collections;
import java.util.List;

public class SingleAnswer extends GeneralAnswer{
    private final String answer;

    public SingleAnswer(String questionID, String answer) {
        super(questionID);
        this.answer = answer;
    }

    @Override
    public List<String> getAnswers() {
        return Collections.singletonList(answer);
    }

    public String getAnswer() {
        return answer;
    }
}
