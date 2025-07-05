package classes;

import java.util.List;

public abstract class GeneralAnswer {
    protected final String questionID;

    public GeneralAnswer(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionID() {
        return questionID;
    }

    /**
     * Returns a list od all possible answers (can be one or many).
     */
    public abstract List<String> getAnswers();
}
