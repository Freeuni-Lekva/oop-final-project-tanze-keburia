package classes;

import java.util.Date;

public class RealQuiz {
    private String quizName;
    private String index;
    private Date date;
    private String author;
    private String type;
    private int questionQuantity;
    private String topic;
    private int timeLimit;
    private boolean visible;

    public RealQuiz(String author, Date date, String index, String type, String quizName) {
        this.quizName = quizName;
        this.index = index;
        this.date = date;
        this.author = author;
        this.type = type;
        this.visible = false;
    }

    public String getName() {
        return quizName;
    }

    public String getID() {
        return index;
    }

    public String getAuthor() {
        return author;
    }

    public String getNumQuestions(){
        return String.valueOf(questionQuantity);
    }

    public String getTopic(){
        return topic;
    }

    public Date getCreationDate(){
        return date;
    }

    public int getTimeLimit(){
        return timeLimit;
    }

    public String getType(){
        return type;
    }

    public void setVisible(boolean param){
        visible = param;
    }

    public void setNumQuestions(int numQuestions){
        this.questionQuantity = numQuestions;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public void setTimeLimit(int timeLimit){
        this.timeLimit = timeLimit;
    }

    public void setName(String newName){
        this.quizName = newName;
    }

}
