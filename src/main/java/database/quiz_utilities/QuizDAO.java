package database.quiz_utilities;

import classes.quiz_utilities.quiz.Quiz;

import java.util.List;

public interface QuizDAO {
    List<Quiz> getAll();
    List<Quiz> getAllbyTopic(String topic);
    List<Quiz> getAllbyAuthor(String author);
    List<Quiz> getAllByType(String type);
    /**
     * initialize
    this should create table and drop previous if it existed
    */
    void initialize();
    void addQuiz(Quiz quiz);
    void removeQuiz(Quiz quiz);
    /*
    newQuiz should have id of modified quiz,
    and contents of target modification
     */
    void modifyQuiz(Quiz newQuiz);
    int getNumQuizes();


    Quiz getQuiz(String id);

    String getQuizNameById(String quizId);

}