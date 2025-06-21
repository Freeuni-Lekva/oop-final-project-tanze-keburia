package database;

import classes.Question;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MockQuestionDAO implements QuestionDAO {
    private int counter = 0;
    private ArrayList<Question> questions;
    public MockQuestionDAO(Connection con) {};
    public void initialize() {
        questions = new ArrayList<>();
    }
    public void addQuestion(Question question) {
        questions.add(question);
    }
    public void removeQuestion(Question question) {
        for(Question q: questions) {
            if(q.equals(question)) {
                questions.remove(q);
                break;
            }
        }
    }
    public void modifyQuestion(Question question) {
        for(Question q: questions) {
            if(q.equals(question)) {
                q = question;
                break;
            }
        }
    }

    public List<Question> getAllQuestions() {
        return new ArrayList<>(this.questions);
    }
    public List<Question> getQuiz(int quizID) {
        ArrayList<Question> res = new ArrayList<>();
        for(Question q: questions) {
            if(q.getQuizID() == quizID) {
                res.add(q);
            }
        }
        return res;
    }
    public Question getQuestion(int questionID) {
        for(Question q: questions) {
            if(q.getID() == questionID) {
                return q;
            }
        }
        return null;
    }
}
