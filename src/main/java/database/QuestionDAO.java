package database;

import classes.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private final Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
        try(Statement statement= connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "question_description VARCHAR(1000), " +
                    "question_answer VARCHAR(255), " +
                    "question_number VARCHAR(50), " +
                    "quiz_number VARCHAR(50), " +
                    "PRIMARY KEY (question_number, quiz_number))");
        } catch (SQLException e) {
            throw new RuntimeException("Question table initialization failed", e);
        }
    }

    public void addQuestion(Question question) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO questions (question_description, question_answer, question_number, quiz_number) VALUES (?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, question.getDescription());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, question.getQuestionNumber());
            preparedStatement.setString(4, question.getQuizNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add question", e);
        }
    }

    public void deleteQuestion(Question question) {
        if(question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM questions WHERE question_number = ? AND quiz_number = ?"
        )) {
            preparedStatement.setString(1, question.getQuestionNumber());
            preparedStatement.setString(2, question.getQuizNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete question", e);
        }

    }

    public List<Question> getQuestions(String quizNumber) {
        List<Question> questions = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM questions WHERE quiz_number = ? ORDER BY question_number"
        )) {
            preparedStatement.setString(1, quizNumber);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    String description = resultSet.getString("question_description");
                    String answer = resultSet.getString("question_answer");
                    String questionNumber = resultSet.getString("question_number");
                    String quizNum = resultSet.getString("quiz_number");

                    questions.add(new Question(description, answer, questionNumber, quizNum));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get questions", e);
        }

        return questions;
    }

    public void editQuestion(Question question) {
        if(question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE questions SET question_description = ?, question_answer = ? " +
                        "WHERE question_number = ? AND quiz_number = ?"
        )) {
            preparedStatement.setString(1, question.getDescription());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, question.getQuestionNumber());
            preparedStatement.setString(4, question.getQuizNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to edit question", e);
        }
    }


}
