package servlets.quiz_management.question_management;

import classes.quiz_utilities.options.Option;
import classes.quiz_utilities.questions.Question;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.OptionsDAO;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.RealQuestionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/multipleChoice")
public class ViewMultipleQuestionServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String questionID = request.getParameter("id");
        //Question question = (Question)request.getAttribute("question");
        try(Connection conn = DatabaseConnector.getInstance().getConnection()) {
            Question question = new RealQuestionDAO(conn).getQuestion(questionID);
            OptionsDAO optionsDAO = new OptionsDAO(conn);
            List<Option> optionList = optionsDAO.getOptionsByQuestion(question.getID());
            request.setAttribute("options", optionList);
            request.setAttribute("question", question);
            request.getRequestDispatcher("multipleChoiceQuestion.jsp").forward(request, response);
        }catch(SQLException e) {
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Could not connect database");
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
