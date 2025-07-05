package classes;

public interface AnswerChecker {
    /**
     * Checks if the provided answer is correct for the given question.
     *
     * @param questionID the ID of the question to check
     * @param userAnswer the user's answer input
     * @return true if the answer is correct, false otherwise
     */
    boolean isCorrect(String questionID, GeneralAnswer userAnswer);
}
