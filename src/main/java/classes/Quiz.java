package classes;

import java.util.Date;

public interface Quiz {

    String getName();
    int getID();
    String getAuthor();

    int getNumQuestions();

    String getTopic();
    /*
    returns the date when quiz was created
     */
    Date getCreationDate();
    /*
    returns time limit for the quiz, infinity if there is no time limit
     */
    int getTimeLimit();
    /*
    type of the quiz: Fill in Blank, multiple choices or other
     */
    String getType();



    void setNumQuestions(int numQuestions);
    void setTopic(String topic);
    void setTimeLimit(int timeLimit);
    void setName(String newName);

}
