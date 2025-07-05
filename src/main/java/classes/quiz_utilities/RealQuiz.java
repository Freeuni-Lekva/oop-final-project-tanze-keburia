package classes.quiz_utilities;

import java.util.Date;

public class RealQuiz implements Quiz {
    private String author;
    private Date date;
    private String id;
    private String type;
    private String name;
    private int numQuestions;
    private String topic;
    private int timeLimit;
    private boolean visible;

    public RealQuiz(String author, Date date, String id, String type, String name) {
        this.author = author;
        this.date = date;
        this.id = id;
        this.type = type;
        this.name = name;
        this.visible = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public int getNumQuestions() {
        return numQuestions;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Date getCreationDate() {
        return date;
    }

    @Override
    public int getTimeLimit() {
        return timeLimit;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setVisible(boolean param) {
        this.visible = param;
    }

    @Override
    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }
}