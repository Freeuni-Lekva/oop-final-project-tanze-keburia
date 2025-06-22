package database;

import classes.RealQuiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    Connection conn;

    public QuizDAO(Connection conn) {
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

    public void createQuiz(RealQuiz quiz) throws SQLException {
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

    public List<RealQuiz> topicFilter(String topic) throws SQLException {
        List<RealQuiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE topic = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, topic);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Quiz quiz = new Quiz(
                        resultSet.getString("quiz_name"),
                        resultSet.getInt("quiz_index"),
                        new Date(resultSet.getTimestamp("creation_date").getTime()),
                        resultSet.getString("author"),
                        resultSet.getInt("time_limit"),
                        resultSet.getInt("question_quantity"),
                        resultSet.getString("topic")
                );
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to filter quizzes by topic", e);
        }
        return quizzes;
    }

    public boolean quizDelete(int quizIndex) {
        String sql = "DELETE FROM quizzes WHERE quiz_index = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, quizIndex);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) return true;
            return false;
        } catch (SQLException e) {
            System.err.println("Error deleting quiz: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public List<RealQuiz> getQuizzes() throws SQLException {
        List<RealQuiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes ORDER BY quiz_index";

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                RealQuiz quiz = new RealQuiz(
                        resultSet.getString("quiz_name"),
                        resultSet.getInt("quiz_index"),
                        new Date(resultSet.getTimestamp("creation_date").getTime()),
                        resultSet.getString("author"),
                        resultSet.getInt("time_limit"),
                        resultSet.getInt("question_quantity"),
                        resultSet.getString("topic")
                );
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve quizzes: " + e.getMessage(), e);
        }
        return quizzes;
    }

}



