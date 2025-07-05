package classes;

import java.util.List;
import java.util.Map;

public interface QuizChecker {
    /**
     * Checks all answers submitted for the quiz.
     *
     * @param questions the list of quiz questions
     * @param userAnswers a map from question ID to user answer
     * @return a map from question ID to whether the answer was correct (T/F)
     */
    Map<String, Boolean> checkAnswer(List<Question> questions, Map<String, String> userAnswers);
}
