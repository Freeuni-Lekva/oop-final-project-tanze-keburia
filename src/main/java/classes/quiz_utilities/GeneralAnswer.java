package classes.quiz_utilities;

import java.util.List;

public abstract class GeneralAnswer {
    protected final String questionID;

    public GeneralAnswer(String questionID) {
        this.questionID = questionID;
    }

    protected GeneralAnswer() {
        questionID = null;
    }

    public String getQuestionID() {
        return questionID;
    }

    /**
     * Returns a list od all possible answers (can be one or many).
     */
    public abstract List<String> getAnswers();
}
