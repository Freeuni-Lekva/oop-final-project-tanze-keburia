package classes;

import java.util.Date;

public class Quiz {
    private String quizName;
    private int index;
    private Date date;
    private String author;
    private int timeLimit;
    private int questionQuantity;
    private String topic;

    public Quiz(String quizName, int index, Date date, String author, int timeLimit, int questionQuantity, String topic) {
        this.quizName = quizName;
        this.index = index;
        this.date = date;
        this.author = author;
        this.timeLimit = timeLimit;
        this.questionQuantity = questionQuantity;
        this.topic = topic;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getIndex() {
        return index;
    }

    public Date getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getQuestionQuantity() {
        return questionQuantity;
    }

    public String getTopic() {
        return topic;
    }
}
