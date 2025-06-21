package classes;

public interface Question {
    /*
    Question(int quizId, int id);
     */
    String getStatement();
    String getAnswer();
    int getQuizID();
    int getID();
    /*
    number of points you get when you answer question correctly;
     */
    double getPoints();

    void setStatement(String statement);
    void setAnswer(String answer);
    void setPoints(double points);

}
