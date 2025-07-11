package classes.quiz_utilities.questions;


import java.util.Objects;

public class RealQuestion implements Question {

    private String statement;
    private String answer;
    private String questionID;
    private String quizID;
    private String points;

    public RealQuestion(String statement, String answer, String quizID, String questionID, String points) {
        this.statement = statement;
        this.answer = answer;
        this.questionID =  questionID;
        this.quizID = quizID;
        this.points = points;
    }

    public String getStatement() {
        return statement;
    }

    public String getAnswer() {
        return answer;
    }

    public String getID() {
        return questionID;
    }

    public String getQuizID() {
        return quizID;
    }

    public double getPoints() {
        return Double.parseDouble(points);
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setPoints(double points) {
        this.points = Double.toString(points);
    }
    public String getPrompt() {
        String[] parts = this.statement.split(";;");
        return parts.length > 0 ? parts[0] : "";
    }

    public String getImageURL() {
        String[] parts = this.statement.split(";;");
        return parts.length > 1 ? parts[1] : "";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof RealQuestion)) return false;
        RealQuestion realQuestion = (RealQuestion) o;

        return Objects.equals(statement, realQuestion.statement)
                && Objects.equals(answer, realQuestion.answer)
                && Objects.equals(questionID, realQuestion.questionID)
                && Objects.equals(quizID, realQuestion.quizID)
                && Objects.equals(points, realQuestion.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statement, answer, questionID, quizID, points);
    }
}

