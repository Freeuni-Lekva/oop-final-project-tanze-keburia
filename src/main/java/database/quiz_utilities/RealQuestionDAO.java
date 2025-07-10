package database.quiz_utilities;

import classes.quiz_utilities.questions.Question;
import classes.quiz_utilities.questions.RealQuestion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RealQuestionDAO implements QuestionDAO{

    private final Connection connection;

    public RealQuestionDAO(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {
        try (Statement statement = connection.createStatement()) {
           // statement.executeUpdate("Drop table if exists questions");
            statement.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "question_statement VARCHAR(1000), " +
                    "question_answer VARCHAR(255), " +
                    "question_id VARCHAR(50), " +
                    "quiz_id VARCHAR(50), " +
                    "question_points VARCHAR(50), " +
                    "PRIMARY KEY (question_id, quiz_id))");
        } catch (SQLException e) {
            throw new RuntimeException("Question table initialization failed", e);
        }
    }

    public void addQuestion(Question question) {


        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO questions (question_statement, question_answer, question_id, quiz_id, question_points) VALUES (?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, question.getStatement());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, question.getID());
            preparedStatement.setString(4, question.getQuizID());
            preparedStatement.setString(5, Double.toString(question.getPoints()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add question", e);
        }
    }

    public void removeQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM questions WHERE question_id = ? AND quiz_id = ?"
        )) {
            preparedStatement.setString(1, question.getID());
            preparedStatement.setString(2, question.getQuizID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete question", e);
        }

    }

    public List<Question> getQuiz(String quizID) {
        List<RealQuestion> questions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM questions WHERE quiz_id = ? ORDER BY question_id"
        )) {
            preparedStatement.setString(1, quizID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String statement = resultSet.getString("question_statement");
                    String answer = resultSet.getString("question_answer");
                    String questionID = resultSet.getString("question_id");
                    String quizNum = resultSet.getString("quiz_id");
                    String points = resultSet.getString("question_points");
                    questions.add(new RealQuestion(statement, answer,  quizNum,questionID, points));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get questions", e);
        }

        return new ArrayList<>(questions);
    }

    public void modifyQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE questions SET question_statement = ?, question_answer = ? , question_points = ? " +
                        "WHERE question_id = ? AND quiz_id = ?"
        )) {
            preparedStatement.setString(1, question.getStatement());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, Double.toString(question.getPoints()));
            preparedStatement.setString(4, question.getID());
            preparedStatement.setString(5, question.getQuizID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to edit question", e);
        }
    }

    @Override



    public List<Question> getAllQuestions() {
        List<RealQuestion> questions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM questions ORDER BY quiz_id, question_id"
        )) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String statement = resultSet.getString("question_statement");
                    String answer = resultSet.getString("question_answer");
                    String questionID = resultSet.getString("question_id");
                    String quizID = resultSet.getString("quiz_id");
                    String points = resultSet.getString("question_points");

                    questions.add(new RealQuestion(statement, answer, questionID, quizID, points));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all questions", e);
        }

        return new ArrayList<>(questions);
    }

    public Question getQuestion(String questionID) {

        if(questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM questions WHERE question_id = ?"
        )) {
            preparedStatement.setString(1, questionID);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    String statement = resultSet.getString("question_statement");
                    String answer = resultSet.getString("question_answer");
                    String quizID = resultSet.getString("quiz_id");
                    String points = resultSet.getString("question_points");

                    return new RealQuestion(statement, answer,quizID,  questionID, points);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get question", e);
        }

        return null;

    }

}

