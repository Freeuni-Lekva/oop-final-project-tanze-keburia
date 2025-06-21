package classes;

import java.util.List;

public interface QuizDAO {
    List<Quiz> getAll();
    List<Quiz> getAllbyTopic(String topic);
    List<Quiz> getAllbyAuthor(String author);
    List<Quiz> getAllByType(String type);
    void addQuiz(Quiz quiz);
    void removeQuiz(Quiz quiz);
    /*
    newQuiz should have id of modified quiz,
    and contents of target modification
     */
    void modifyQuiz(Quiz newQuiz);
}
