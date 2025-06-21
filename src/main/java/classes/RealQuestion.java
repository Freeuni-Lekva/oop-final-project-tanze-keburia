package classes;


public class RealQuestion {

    private final String description;
    private final String answer;
    private final String questionNumber;
    private final String quizNumber;

    public RealQuestion(String description, String answer, String questionNumber, String quizNumber) {
        this.description = description;
        this.answer = answer;
        this.questionNumber = questionNumber;
        this.quizNumber = quizNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public String getQuizNumber() {
        return quizNumber;
    }

}

//public interface Question {
//    String getStatement();
//    String getAnswer();
//    int getQuizID();
//    int getID();
//    /*
//    number of points you get when you answer question correctly;
//     */
//    double getPoints();
//
//    void setStatement(String statement);
//    void setAnswer(String answer);
//    void setPoints(double points);
//
//}
