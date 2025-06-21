package classes;

public class MockQuestion implements Question {
    private String question;
    private String answer;
    private double mark;
    private int quizId;
    private int id;
    public MockQuestion(String question, String answer, String quizID, String id){
        this.quizId = Integer.parseInt(quizID);
        this.id = Integer.parseInt(id);
    }
    public String getStatement() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public double getPoints() {
        return mark;
    }
    public int getQuizID() {
        return quizId;
    }
    public int getID() {
        return id;
    }
    public void setStatement(String question) {
        this.question = question;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public void setPoints(double mark) {
        this.mark = mark;
    }
    public boolean equals(Question question){
        if(question.getID() == this.id){
            return true;
        }
        return false;
    }
}
