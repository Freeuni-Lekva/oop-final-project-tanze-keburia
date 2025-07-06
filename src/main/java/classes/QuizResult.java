package classes;


import java.sql.Timestamp;

public class QuizResult {
    private String userName;
    private String quizId;
    private int score;
    private Timestamp submitTime;

    public QuizResult(String userName, String quizId, int score, Timestamp submitTime) {
        this.userName = userName;
        this.quizId = quizId;
        this.score = score;
        this.submitTime = submitTime;
    }

    public String getUserName() { return userName; }
    public String getQuizId() { return quizId; }
    public int getScore() { return score; }
    public Timestamp getSubmitTime() { return submitTime; }
}