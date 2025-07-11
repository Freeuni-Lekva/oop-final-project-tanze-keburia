package classes.quiz_result;

import classes.quiz_utilities.quiz.Quiz;

import java.sql.Timestamp;

public final class QuizResult {
    private String username;
    private String quizID;
    private String quizName;
    private double score;
    private Timestamp submitTime;

    public QuizResult(String username, String quizID, String quizName, double score, Timestamp submitTime) {
        this.username = username;
        this.quizID = quizID;
        this.quizName = quizName;
        this.score = score;
        this.submitTime = submitTime;
    }

    public String getUsername() { return username; }
    public String getQuizId() { return quizID;}
    public String getQuizName() {return quizName;}
    public double getScore() { return score; }
    public Timestamp getSubmitTime() { return submitTime; }

}