package classes;

import classes.quiz_utilities.Quiz;
import classes.quiz_utilities.RealQuiz;

import java.sql.Timestamp;

public class QuizResult {
    private String username;
    private Quiz quiz;
    private double score;
    private Timestamp submitTime;

    public QuizResult(String username, Quiz quiz, double score, Timestamp submitTime) {
        this.username = username;
        this.quiz = quiz;
        this.score = score;
        this.submitTime = submitTime;
    }

    public String getUsername() { return username; }
    public String getQuizId() { return quiz.getID(); }
    public String getQuizName() {return quiz.getName();}
    public double getScore() { return score; }
    public Timestamp getSubmitTime() { return submitTime; }
}