package database.quiz_utilities;

import classes.quiz_utilities.options.Option;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionsDAO {
    private final Connection conn;
    public OptionsDAO(Connection conn) {
        this.conn = conn;
    }
    public void initialize() throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS options");
        stmt.execute(
                "CREATE TABLE IF NOT EXISTS options (" +
                "answer VARCHAR(1000), " +
                "option_id VARCHAR(255), " +
                "question_id VARCHAR(250), " +
                "option_points DOUBLE, " +
                "PRIMARY KEY (option_id))");
        stmt.close();
    }
    public void addOption(Option toAdd) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(
                "INSERT INTO options (answer, option_id, question_id, option_points) VALUES (?, ?, ?, ?)"
        );
        {
            stmt.setString(1, toAdd.getAnswer());
            stmt.setString(2, toAdd.getOptionID());
            stmt.setString(3, toAdd.getQuestionID());
            stmt.setDouble(4, toAdd.getPoints());
        };
        stmt.executeUpdate();
        stmt.close();
    }
    public void removeOption(String removeID) throws SQLException {
        PreparedStatement stmt = null;
        stmt =  conn.prepareStatement(
                "DELETE FROM options WHERE option_id = ?");
        {
            stmt.setString(1, removeID);
        }
        stmt.executeUpdate();
        stmt.close();
    }
    public void updateOption(Option option) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(
                "UPDATE options SET answer = ?, question_id = ?, option_points = ? WHERE option_id = ?");
        {
            stmt.setString(1, option.getAnswer());
            stmt.setString(2, option.getQuestionID());
            stmt.setDouble(3, option.getPoints());
            stmt.setString(4, option.getOptionID());
        }
        stmt.executeUpdate();
        stmt.close();
    }
    public List<Option> getOptionsByQuestion(String questionID) throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT * FROM options WHERE question_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, questionID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    options.add(new Option(
                            rs.getString("question_id"),
                            rs.getString("option_id"),

                            rs.getString("answer"),
                            rs.getDouble("option_points")
                    ));
                }
            }
        }
        return options;  // return here after resources are closed
    }

    public Option getOptionByID(String optionID) throws SQLException {
        try(
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM options WHERE option_id = ?"
        )){
            stmt.setString(1, optionID);

            try(ResultSet rs = stmt.executeQuery()){

                if (rs.next()) {
                    return new Option(
                            rs.getString("question_id"),
                            rs.getString("option_id"),
                            rs.getString("answer"),
                            rs.getDouble("option_points")
                    );
                }
            }
        }
        return null;
    }
    public int getNumberOfOptions() throws SQLException {
        String query = "SELECT COUNT(*) FROM options";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if(rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}
