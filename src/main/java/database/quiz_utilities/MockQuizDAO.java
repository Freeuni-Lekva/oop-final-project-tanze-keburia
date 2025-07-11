package database.quiz_utilities;

import classes.quiz_utilities.quiz.Quiz;

import java.sql.Connection;
import java.sql.SQLException;
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

    @Override
    public String getQuizNameById(String quizId) {
        return "";
    }

    @Override
    public List<Quiz> getRecentQuizzes(int limit) throws SQLException {
        return quizzes.stream()
                .sorted((q1, q2) -> q2.getCreationDate().compareTo(q1.getCreationDate()))
                .limit(limit)
                .toList();
    }

    @Override
    public List<Quiz> getPopularQuizzes(int limit) throws SQLException {
        return quizzes.stream()
                .sorted((q1, q2) -> Integer.compare(q2.getPlayCount(), q1.getPlayCount()))
                .limit(limit)
                .toList();
    }

    @Override
    public void incrementPlayCount(String quizId) {
        for (Quiz q : quizzes) {
            if (q.getID().equals(quizId)) {
                q.setPlayCount(q.getPlayCount() + 1);
                break;
            }
        }
    }

    @Override
    public List<Quiz> getRecentlyCreatedQuizzesByUser(String username, int limit) throws SQLException {
        return quizzes.stream()
                .filter(q -> q.getAuthor().equals(username))
                .sorted((q1, q2) -> q2.getCreationDate().compareTo(q1.getCreationDate()))
                .limit(limit)
                .toList();
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