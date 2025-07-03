package classes;

public interface Question {
    /*
    Question(String description, String answer, String quizId, String id, String points);
     */
    String getStatement();
    String getAnswer();
    String getQuizID();
    String getID();

    /*
    number of points you get when you answer question correctly;
     */
    double getPoints();

    void setStatement(String statement);
    void setAnswer(String answer);
    void setPoints(double points);


}

