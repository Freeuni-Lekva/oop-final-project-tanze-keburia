package classes;

import java.util.Date;

public class MockQuiz implements Quiz{
    private int id;
    private String author;
    private String name;
    private String topic;
    private int numQuestions;
    private Date creationDate;
    private int timeLimit;
    private String type;
    public MockQuiz(int id, String author, Date created, String type){
        this.id = id;
        this.author = author;
        this.creationDate = created;
        this.type = type;
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

    public int getID() {
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
}
