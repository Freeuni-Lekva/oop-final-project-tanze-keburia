package classes.quiz_utilities.checkers;

import classes.quiz_utilities.answer.GeneralAnswer;

import java.sql.Connection;
import java.sql.SQLException;

public interface AnswerChecker {
    /**
     * Checks if the provided answer is correct for the given question and returns appropriate points.
     *
     * @param questionID the ID of the question to check
     * @param userAnswer the user's answer input
     * @return points in double according to answer correctness
     */
    double getPoints(String questionID, GeneralAnswer userAnswer) throws SQLException;
    public void setDAO(Connection conn);
}
