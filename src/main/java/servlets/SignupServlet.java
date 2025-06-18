package servlets;

import classes.User;
import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ServletContext context = request.getServletContext();
        UserDAO userDAO = (UserDAO)context.getAttribute("users");
        if(userDAO.userExists(username)) {
            response.sendRedirect("usedName.jsp");
        }
        else {
            User newUser = null;
            try {
                newUser = new User(username, password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            userDAO.addUser(newUser);
            response.sendRedirect("homepage.jsp");
        }
    }
}
