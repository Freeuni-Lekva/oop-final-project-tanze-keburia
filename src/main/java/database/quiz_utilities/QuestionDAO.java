package database.quiz_utilities;

import classes.quiz_utilities.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    List<Question> getQuiz(String quizID);

    /**
     * initialize table
     * this should create table and drop previous if it exists
     */
  //  Question getQuestion(String questionID);
    void initialize();
    void addQuestion(Question question);
    void removeQuestion(Question question);
    void modifyQuestion(Question question);

    Question getQuestion(String id);
}
