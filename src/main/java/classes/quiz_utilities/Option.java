package classes.quiz_utilities;

public class Option {
    private String questionID;
    private String optionID;
    private String answer;
    private double points;
    public Option(String questionID, String optionID, String answer, double points) {
        this.questionID = questionID;
        this.optionID = optionID;
        this.answer = answer;
        this.points = points;
    }
    public Option(Option option) {
        this.questionID = option.getQuestionID();
        this.optionID = option.getOptionID();
        this.answer = option.getAnswer();
        this.points = option.getPoints();
    }
    public double getPoints() {
        return points;
    }
    public void setPoints(double points) {
        this.points = points;
    }
    public String getQuestionID() {
        return this.questionID;
    }
    public String getOptionID() {
        return this.optionID;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
