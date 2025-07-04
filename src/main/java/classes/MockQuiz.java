package classes;

import java.util.Date;

public class MockQuiz implements Quiz{
    private String id;
    private String author;
    private String name;
    private String topic;
    private int numQuestions;
    private Date creationDate;
    private int timeLimit;
    private String type;
    private boolean visible;
    public MockQuiz(String author, Date created, String id,  String type, String name){
        this.id = id;
        this.author = author;
        this.creationDate = created;
        this.type = type;
        this.name = name;
        timeLimit = (int)1e9;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getID() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
    public String getName() {
        return name;
    }
    public String getTopic() {
        return topic;
    }
    public int getNumQuestions() {
        return numQuestions;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public int getTimeLimit() {
        return timeLimit;
    }
    public String getType() {
        return type;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
