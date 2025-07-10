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
                    "challenge_id VARCHAR(255), " +
                    "sender VARCHAR(255), " +
                    "receiver VARCHAR(255), " +
                    "quiz_id VARCHAR(255), "+
                    "quiz_name VARCHAR(255), "+
                    "score DOUBLE, "+
                    "PRIMARY KEY (challenge_id))");
        }
    }
    public void addChallenge(Challenge x) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO challenges (challenge_id, sender, receiver, " +
                        "quiz_id, quiz_name, score) VALUES(?, ?, ?, ?, ?, ?)"
        )){
            stmt.setString(1, x.getId());
            stmt.setString(2, x.getSender());
            stmt.setString(3, x.getReceiver());
            stmt.setString(4, x.getQuizID());
            stmt.setString(5, x.getQuizName());
            stmt.setDouble(6, x.getScore());
            stmt.executeUpdate();
        }
    }
    public void removeChallenge(String ChallengeID) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM challenges WHERE challenge_id = ?"
        )){
           stmt.setString(1, ChallengeID);
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
                        rs.getString("challenge_id"),
                        rs.getString("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getDouble("score")
                ));
            }
            return challenges;
        }

    }

}
