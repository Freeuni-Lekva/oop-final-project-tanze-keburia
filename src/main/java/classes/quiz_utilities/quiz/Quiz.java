package classes.quiz_utilities.quiz;

import java.util.Date;

public interface Quiz {
    /*
    visible is false when initializing
    Quiz(String author, Date date, String id, String type, String name);
     */
    String getName();
    String getID();
    String getAuthor();

    int getNumQuestions();

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

    String getPageFormat();

    void setVisible(boolean visible);

    void setPageFormat(String pageFormat);
    void setNumQuestions(int numQuestions);
    void setTopic(String topic);
    void setTimeLimit(int timeLimit);
    void setName(String newName);

    int getPlayCount();
    void setPlayCount(int playCount);

}
