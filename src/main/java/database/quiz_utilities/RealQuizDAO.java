package database.quiz_utilities;

import classes.quiz_utilities.quiz.Quiz;
import classes.quiz_utilities.quiz.RealQuiz;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RealQuizDAO implements QuizDAO {
    private final Connection conn;

    public RealQuizDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS quizzes");

            stmt.executeUpdate("CREATE TABLE quizzes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "quiz_id VARCHAR(255) NOT NULL UNIQUE, " +
                    "quiz_name VARCHAR(255) NOT NULL, " +
                    "creation_date DATETIME NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "time_limit INT NOT NULL, " +
                    "question_quantity INT NOT NULL, " +
                    "topic VARCHAR(255),  " +
                    "type VARCHAR(255) NOT NULL, " +
                    "visible BOOLEAN NOT NULL DEFAULT FALSE, " +
                    "page_format VARCHAR(255) NOT NULL DEFAULT 'All Questions on One Page', " +
                    "play_count INT NOT NULL DEFAULT 0" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize quiz database", e);
        }
    }

    @Override
    public void addQuiz(Quiz quiz) {
        String sql = "INSERT INTO quizzes (quiz_id, quiz_name, creation_date, author, " +
                "time_limit, question_quantity, topic, type, page_format, play_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quiz.getID());
            stmt.setString(2, quiz.getName());
            stmt.setTimestamp(3, new Timestamp(quiz.getCreationDate().getTime()));
            stmt.setString(4, quiz.getAuthor());
            stmt.setInt(5, quiz.getTimeLimit());
            stmt.setInt(6, quiz.getNumQuestions());
            stmt.setString(7, quiz.getTopic());
            stmt.setString(8, quiz.getType());
            stmt.setString(9, quiz.getPageFormat());
            stmt.setInt(10, quiz.getPlayCount());


            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add quiz", e);
        }
    }

    @Override
    public Quiz getQuiz(String id) {
        String sql = "SELECT * FROM quizzes WHERE quiz_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createQuizFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quiz", e);
        }
    }

    @Override
    public List<Quiz> getAll() {
        return getQuizzesWithQuery("SELECT * FROM quizzes");
    }

    @Override
    public List<Quiz> getAllbyTopic(String topic) {
        return getQuizzesWithQuery("SELECT * FROM quizzes WHERE topic = ?", topic);
    }

    @Override
    public List<Quiz> getAllbyAuthor(String author) {
        return getQuizzesWithQuery("SELECT * FROM quizzes WHERE author = ?", author);
    }

    @Override
    public List<Quiz> getAllByType(String type) {
        return getQuizzesWithQuery("SELECT * FROM quizzes WHERE type = ?", type);
    }

    @Override
    public void removeQuiz(Quiz quiz) {
        String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quiz.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove quiz", e);
        }
    }

    @Override
    public void modifyQuiz(Quiz newQuiz) {
        String sql = "UPDATE quizzes SET quiz_name = ?, creation_date = ?, author = ?, " +
                "time_limit = ?, question_quantity = ?, topic = ?, type = ?, page_format = ?, play_count = ? " +
                "WHERE quiz_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newQuiz.getName());
            stmt.setTimestamp(2, new Timestamp(newQuiz.getCreationDate().getTime()));
            stmt.setString(3, newQuiz.getAuthor());
            stmt.setInt(4, newQuiz.getTimeLimit());
            stmt.setInt(5, newQuiz.getNumQuestions());
            stmt.setString(6, newQuiz.getTopic());
            stmt.setString(7, newQuiz.getType());
            stmt.setString(8, newQuiz.getPageFormat());
            stmt.setInt(9, newQuiz.getPlayCount());
            stmt.setString(10, newQuiz.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to modify quiz", e);
        }
    }

    @Override
    public int getNumQuizes() {
        String sql = "SELECT COUNT(*) FROM quizzes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count quizzes", e);
        }
    }

    private List<Quiz> getQuizzesWithQuery(String query, String... param) {
        List<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (param.length > 0) {
                stmt.setString(1, param[0]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    quizzes.add(createQuizFromResultSet(rs));
                }
            }
            return quizzes;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve quizzes", e);
        }
    }

    private Quiz createQuizFromResultSet(ResultSet rs) throws SQLException {
        Quiz quiz = new RealQuiz(
                rs.getString("author"),
                new Date(rs.getTimestamp("creation_date").getTime()),
                rs.getString("quiz_id"),
                rs.getString("type"),
                rs.getString("quiz_name"),
                rs.getString("page_format")
        );
        quiz.setNumQuestions(rs.getInt("question_quantity"));
        quiz.setTopic(rs.getString("topic"));
        quiz.setTimeLimit(rs.getInt("time_limit"));
        quiz.setPageFormat(rs.getString("page_format"));
        quiz.setPlayCount(rs.getInt("play_count"));


        if (rs.getBoolean("visible")) {
            quiz.setVisible(true);
        }

        return quiz;
    }

    @Override
    public String getQuizNameById(String quizId) {
        String sql = "SELECT quiz_name FROM quizzes WHERE quiz_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quizId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("quiz_name");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quiz name", e);
        }
    }
    public int getCreatedQuizCount(String username) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM quizzes WHERE author = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return 0;
    }



    @Override
    public List<Quiz> getRecentQuizzes(int limit) throws SQLException {
        String sql = "SELECT * FROM quizzes ORDER BY creation_date DESC LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            return mapResultSetToQuizzes(rs);
        }
    }

    @Override
    public List<Quiz> getPopularQuizzes(int limit) throws SQLException {
        String sql = "SELECT * FROM quizzes ORDER BY play_count DESC LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            return mapResultSetToQuizzes(rs);
        }
    }

    @Override
    public void incrementPlayCount(String quizId) {
        String sql = "UPDATE quizzes SET play_count = play_count + 1 WHERE quiz_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quizId);
            stmt.executeUpdate();
        } catch (SQLException e) {throw new RuntimeException("Failed to increment play count", e);}
    }


    @Override
    public List<Quiz> getRecentlyCreatedQuizzesByUser(String username, int limit) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE author = ? ORDER BY creation_date DESC LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            return mapResultSetToQuizzes(rs);
        } catch(SQLException e) {throw new RuntimeException("Failed to get recently created quizzes", e);}
    }



    private List<Quiz> mapResultSetToQuizzes(ResultSet rs) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        while(rs.next()) {
            quizzes.add(createQuizFromResultSet(rs));
        }
        return quizzes;
    }

}
