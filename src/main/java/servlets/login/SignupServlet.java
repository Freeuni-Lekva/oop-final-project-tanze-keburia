package servlets.login;


import classes.User;
import database.database_connection.DatabaseConnector;
import database.user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            if (userDAO.userExists(username)) {
                response.sendRedirect("usedName.jsp");
                return;
            }

            User newUser;
            try {
                newUser = new User(username, password);
            } catch (NoSuchAlgorithmException e) {
                throw new ServletException("Password hashing failed", e);
            }

            userDAO.addUser(newUser);

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("Homepage");

        } catch (SQLException e) {
            throw new ServletException("Database error during signup", e);
        }
    }
}
