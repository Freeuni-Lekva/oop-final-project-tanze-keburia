package database;

import classes.QuizResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockQuizHistoryDAO {
    Map<String, List<QuizResult> > quizResults;

    public MockQuizHistoryDAO(Connection con){}

    public void initialize() {
        quizResults = new HashMap<>();
    }

    public void AddQuizResult(QuizResult result) {
        String userName = result.getUserName();
        if(!quizResults.containsKey(userName)){
            List<QuizResult> lst = new ArrayList<>();
            lst.add(result);
            quizResults.put(userName, lst);
            return;
        }
        quizResults.get(userName).add(result);
    }

    public List<QuizResult> getUserHistory(String userName) {
        return quizResults.get(userName);
    }
}
