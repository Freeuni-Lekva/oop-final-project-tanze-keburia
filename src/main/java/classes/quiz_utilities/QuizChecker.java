package classes.quiz_utilities;


import java.util.Map;

public interface QuizChecker {
    /**
     * Checks all answers submitted for the quiz.
     *
     * @param userAnswers a map from question ID to user answers
     * @return a map from question ID to whether the answer was correct (T/F)
     */
    Map<String, Double> checkAnswer(Map<String, GeneralAnswer> userAnswers);
}
