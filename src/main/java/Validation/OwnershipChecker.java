package Validation;

import classes.Quiz;
import database.QuizDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OwnershipChecker {
    public static boolean checkOwnershipByID(QuizDAO quizzes, HttpServletRequest request,
                                         HttpServletResponse response, String quizId) throws IOException {
            HttpSession session = request.getSession(false);
            //QuizDAO quizzes = (QuizDAO)context.getAttribute("quizzes");
            System.out.println(quizzes);
            if(quizzes == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Initialization Error");
                return false;
            }
            String user = session.getAttribute("username").toString();
            Quiz currentQuiz = quizzes.getQuiz(quizId);
            return checkOwnershipByQuiz(currentQuiz, request, response, user);
    }
    public static boolean checkOwnershipByQuiz(Quiz currentQuiz, HttpServletRequest request, HttpServletResponse response, String user) throws IOException {
        if(!currentQuiz.getAuthor().equals(user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to perform this action");
            return false;
        }
        return true;
    }
}
