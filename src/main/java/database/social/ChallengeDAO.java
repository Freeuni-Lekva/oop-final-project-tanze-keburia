package database.social;

import classes.social.Challenge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDAO {
    private final Connection conn;
    public ChallengeDAO(Connection connection) {
        this.conn = connection;
    }
    public void initialize() throws SQLException {
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS challenges");
            stmt.executeUpdate("CREATE TABLE challenges(" +
                    "challenge_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "sender VARCHAR(255), " +
                    "receiver VARCHAR(255), " +
                    "quiz_id VARCHAR(255), "+
                    "quiz_name VARCHAR(255), "+
                    "score DOUBLE)");
        }
    }
    public void addChallenge(Challenge x) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO challenges ( sender, receiver, " +
                        "quiz_id, quiz_name, score) VALUES( ?, ?, ?, ?, ?)"
        )){

            stmt.setString(1, x.getSender());
            stmt.setString(2, x.getReceiver());
            stmt.setString(3, x.getQuizID());
            stmt.setString(4, x.getQuizName());
            stmt.setDouble(5, x.getScore());
            stmt.executeUpdate();
        }
    }

    public void removeChallenge(Challenge x) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM challenges WHERE quiz_id = ? " +
                        "and sender = ? and receiver = ? " +
                        "and ABS(score - ?) < 0.000001 and quiz_name = ?"
        )){
           stmt.setString(1, x.getQuizID());
           stmt.setString(2, x.getSender());
           stmt.setString(3, x.getReceiver());
           stmt.setDouble(4, x.getScore());
           stmt.setString(5, x.getQuizName());
           stmt.executeUpdate();
        }
    }

    public List<Challenge> getUserChallenges(String userID) throws SQLException {
        List<Challenge> challenges = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM challenges WHERE receiver = ?"
        )){
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                challenges.add(new Challenge(
                        rs.getString("sender"),
                        rs.getString("receiver"),
                        rs.getString("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getDouble("score")
                ));
            }
            return challenges;
        }

    }
    public boolean ChallengeExists(Challenge challenge) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "SELECT *  FROM challenges WHERE quiz_id = ? and sender = ? and receiver = ? and ABS(score - ?)< 0.000001"
        )){
            stmt.setString(1, challenge.getQuizID());
            stmt.setString(2, challenge.getSender());
            stmt.setString(3, challenge.getReceiver());
            stmt.setDouble(4, challenge.getScore());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

}
