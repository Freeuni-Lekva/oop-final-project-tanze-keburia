package database;

import classes.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    List<Question> getQuiz(int quizID);

    /**
     * initialize table
     * this should create table and drop previous if it exists
     */
    void initialize();
    void addQuestion(Question question);
    void removeQuestion(Question question);
    void modifyQuestion(Question question);
}
