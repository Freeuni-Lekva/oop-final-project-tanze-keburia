package Validation;

import classes.Quiz;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OwnershipChecker {
    public static boolean checkOwnership(QuizDAO quizzes, HttpServletRequest request,
                                         HttpServletResponse response, String quizId) throws IOException {
            HttpSession session = request.getSession(false);
            //QuizDAO quizzes = (QuizDAO)context.getAttribute("quizzes");
            if(quizzes == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Initialization Error");
                return false;
            }
            Quiz currentQuiz = quizzes.getQuiz(quizId);
            if(!currentQuiz.getAuthor().equals(session.getAttribute("username"))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to perform this action");
                return false;
            }
            return true;
    }
}
