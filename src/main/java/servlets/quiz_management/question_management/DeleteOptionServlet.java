package servlets.quiz_management.question_management;

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

@WebServlet("/DeleteOption")
public class DeleteOptionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String quizID = request.getParameter("quizID");
        String questionID = request.getParameter("questionID");
        String optionID = request.getParameter("optionID");

        // Validate parameters
        if (quizID == null || questionID == null || optionID == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to get database connection");
            }

            OptionsDAO optionsDAO = new OptionsDAO(conn);
            optionsDAO.removeOption(optionID);

            // Redirect back to the question view page
            response.sendRedirect("multipleChoice?id=" + questionID + "&quizID=" + quizID);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/multipleChoice?id=" + questionID)
                    .forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}