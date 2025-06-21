package classes;

import java.util.Date;

public interface Quiz {
    /*
    Quiz(String author, Date date, String id, String type);
     */
    String getName();
    String getID();
    String getAuthor();

    String getNumQuestions();

    String getTopic();
    /*
    @method getCreationDate
    returns the date when quiz was created
     */
    Date getCreationDate();
    /*
    @method getTimeLimit
    returns time limit for the quiz, infinity if there is no time limit
     */
    int getTimeLimit();
    /*
    @method getType
    type of the quiz: Fill in Blank, multiple choices or other
     */
    String getType();



    void setNumQuestions(int numQuestions);
    void setTopic(String topic);
    void setTimeLimit(int timeLimit);
    void setName(String newName);

}
