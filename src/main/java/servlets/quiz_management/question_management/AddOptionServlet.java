package servlets.quiz_management.question_management;

import classes.quiz_utilities.options.Option;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.OptionsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/AddOption")
public class AddOptionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String quizID = request.getParameter("quizID");
        String questionID = request.getParameter("questionID");
        String optionText = request.getParameter("optionText");
        String pointsStr = request.getParameter("points");

        try {
            double points = Double.parseDouble(pointsStr);

            // Get connection from DatabaseConnector
            try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
                if (conn == null) {
                    throw new SQLException("Failed to get database connection");
                }

                OptionsDAO optionsDAO = new OptionsDAO(conn);
                String optionID = UUID.randomUUID().toString();
                Option newOption = new Option(questionID, optionID, optionText, points);
                optionsDAO.addOption(newOption);

                response.sendRedirect("multipleChoice?id=" + questionID + "&quizID=" + quizID);
            }
        } catch(NumberFormatException e) {
            request.setAttribute("error", "Invalid points value");
            request.getRequestDispatcher("/multipleChoice?id=" + questionID)
                    .forward(request, response);
        } catch(SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/multipleChoice?id=" + questionID)
                    .forward(request, response);
        }
    }
}