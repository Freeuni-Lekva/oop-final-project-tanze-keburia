package servlets.login;

import database.DatabaseConnector;
import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            if (userDAO.userExists(username) && userDAO.checkPassword(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("Homepage");
            } else {
                response.sendRedirect("loginFailed.jsp");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}
