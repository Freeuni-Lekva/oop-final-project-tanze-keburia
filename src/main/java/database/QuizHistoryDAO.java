package database;
import classes.QuizResult;
import classes.quiz_utilities.Quiz;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizHistoryDAO {
    private final Connection conn;
    private QuizDAO quizzes;
    public QuizHistoryDAO(Connection conn, QuizDAO quizzes) {
        this.conn = conn;
        this.quizzes = quizzes;
    }

    public void initialize() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS quiz_history");
            stmt.execute("CREATE TABLE IF NOT EXISTS quiz_history (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL, " +
                    "quiz_id VARCHAR(255) NOT NULL, " +
                    "score DOUBLE NOT NULL, " +
                    "submit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize quiz_history table", e);
        }
    }

    public void addResult(QuizResult result) {
        System.out.println("Hello" + result.getQuizId());
        if (result == null || result.getUsername() == null) {
            throw new IllegalArgumentException("Invalid quiz result");
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO quiz_history (username, quiz_id, score, submit_time) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, result.getUsername());
            ps.setString(2, result.getQuizId());
            ps.setDouble(3, result.getScore());
            ps.setTimestamp(4, result.getSubmitTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert quiz result", e);
        }
    }

    public List<QuizResult> getUserHistory(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        List<QuizResult> history = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM quiz_history WHERE username = ? ORDER BY submit_time DESC")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("quiz_id");
                    Quiz x = quizzes.getQuiz(id);
                    QuizResult result = new QuizResult(
                            rs.getString("username"),
                            x.getID(),
                            x.getName(),
                            rs.getDouble("score"),
                            rs.getTimestamp("submit_time")
                    );
                    history.add(result);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve quiz history", e);
        }
        return history;
    }
}
