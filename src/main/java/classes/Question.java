package classes;


public class Question {

    private final String description;
    private final String answer;
    private final String questionNumber;
    private final String quizNumber;

    public Question(String description, String answer, String questionNumber, String quizNumber) {
        this.description = description;
        this.answer = answer;
        this.questionNumber = questionNumber;
        this.quizNumber = quizNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public String getQuizNumber() {
        return quizNumber;
    }
}
