package classes;

import java.sql.Timestamp;

public class QuizResult {
    private String username;
    private String quizId;
    private int score;
    private Timestamp submitTime;

    public QuizResult(String username, String quizId, int score, Timestamp submitTime) {
        this.username = username;
        this.quizId = quizId;
        this.score = score;
        this.submitTime = submitTime;
    }

    public String getUsername() { return username; }
    public String getQuizId() { return quizId; }
    public int getScore() { return score; }
    public Timestamp getSubmitTime() { return submitTime; }
}