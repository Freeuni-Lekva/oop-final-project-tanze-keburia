package classes;

import classes.quiz_utilities.questions.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestions();
    List<Question> getQuiz(int quizID);

    void addQuestion(Question question);
    void removeQuestion(Question question);
    void modifyQuestion(Question question);
    Question getQuestion(int questionID);
}
