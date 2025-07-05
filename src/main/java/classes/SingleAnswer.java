package classes;

import java.util.Collections;
import java.util.List;

public class SingleAnswer extends GeneralAnswer{
    private final String answer;

    public SingleAnswer(String answer) {
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
