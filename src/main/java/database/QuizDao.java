package database;

import classes.Quiz;

import java.sql.*;
import java.util.List;

public class QuizDao {
    Connection conn;

    public QuizDao(Connection conn) {
        this.conn = conn;
        String sql = "CREATE TABLE IF NOT EXISTS quizzes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "quiz_name VARCHAR(255) NOT NULL, " +
                "quiz_index INT NOT NULL, " +
                "creation_date DATETIME NOT NULL, " +
                "author VARCHAR(255) NOT NULL, " +
                "time_limit INT NOT NULL, " +
                "question_quantity INT NOT NULL, " +
                "topic VARCHAR(255) NOT NULL, " +
                "UNIQUE(quiz_index)" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize quiz database", e);
        }
    }

    public void createQuiz(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (quiz_name, quiz_index, creation_date, author, time_limit, question_quantity, topic) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, quiz.getQuizName());
            statement.setInt(2, quiz.getIndex());
            statement.setTimestamp(3, new Timestamp(quiz.getDate().getTime()));
            statement.setString(4, quiz.getAuthor());
            statement.setInt(5, quiz.getTimeLimit());
            statement.setInt(6, quiz.getQuestionQuantity());
            statement.setString(7, quiz.getTopic());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to create quiz: " + e.getMessage(), e);
        }
    }

//    public List<Integer> topicFilter(String topic)  {
//
//    }

}
