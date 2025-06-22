package database;

import classes.Quiz;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MockQuizDAO implements QuizDAO{

    ArrayList<Quiz> quizzes = new ArrayList<>();

    public MockQuizDAO(Connection con){}

    public void initialize() {
        quizzes = new ArrayList<>();
    }
    public void addQuiz(Quiz quiz){
        quizzes.add(quiz);
    }
    public void removeQuiz(Quiz quiz) {
        for(Quiz q : quizzes) {
            if(q.getID().equals(quiz.getID())) {
                quizzes.remove(q);
                break;
            }
        }
    }
    public void modifyQuiz(Quiz quiz){
        for(Quiz q : quizzes) {
            if(q.getID().equals(quiz.getID())) {
                q = quiz;
                break;
            }
        }
    }
    public Quiz getQuiz(String id) {
        for(Quiz q : quizzes) {
            if(q.getID().equals(id)) {
                return q;
            }
        }
        return null;
    }
    public List<Quiz> getAll() {
        return new ArrayList<>(quizzes);
    }
    public List<Quiz> getAllbyTopic(String topic) {
        ArrayList<Quiz> res = new ArrayList<>();
        for(Quiz q : quizzes) {
            if(q.getTopic().equals(topic)) {
                res.add(q);
            }
        }
        return res;
    }
    public List<Quiz> getAllbyAuthor(String author) {
        ArrayList<Quiz> res = new ArrayList<>();
        for(Quiz q : quizzes) {
            if(q.getAuthor().equals(author)) {
                res.add(q);
            }
        }
        return res;
    }
    public List<Quiz> getAllByType(String type) {
        ArrayList<Quiz> res = new ArrayList<>();
        for(Quiz q : quizzes) {
            if(q.getType().equals(type)) {
                res.add(q);
            }
        }
        return res;
    }
    public int getNumQuizes() {
        return quizzes.size();
    }
}
