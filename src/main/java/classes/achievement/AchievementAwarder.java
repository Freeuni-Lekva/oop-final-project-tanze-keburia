package classes.achievement;

import database.achievement.AchievementDAO;
import database.history.QuizHistoryDAO;
import database.quiz_utilities.QuizDAO;

public class AchievementAwarder {
    private final AchievementDAO achievementDAO;
    private final QuizDAO quizDAO;
    private final QuizHistoryDAO quizHistoryDAO;
    public AchievementAwarder(AchievementDAO achievementDAO, QuizDAO quizDAO, QuizHistoryDAO quizHistoryDAO) {
        this.achievementDAO = achievementDAO;
        this.quizDAO = quizDAO;
        this.quizHistoryDAO = quizHistoryDAO;
    }

    public void checkQuizCreationAchievements(String username) {
        int createdCount = quizDAO.getCreatedQuizCount(username);
        if (createdCount == 1) achievementDAO.awardAchievement(username, "Amateur Author");
        if (createdCount == 5) achievementDAO.awardAchievement(username, "Prolific Author");
        if (createdCount == 10) achievementDAO.awardAchievement(username, "Prodigious Author");
    }

    public void checkQuizParticipationAchievements(String username, String quizId, double scoreJustEarned) {
        int attemptCount = quizHistoryDAO.getUserAttemptCount(username);
        if (attemptCount == 10) {
            achievementDAO.awardAchievement(username, "Quiz Machine");
        }

        double topScore = quizHistoryDAO.getTopScoreForQuiz(quizId);
        if (scoreJustEarned >= topScore) {
            achievementDAO.awardAchievement(username, "I am the Greatest");
        }
    }
}
