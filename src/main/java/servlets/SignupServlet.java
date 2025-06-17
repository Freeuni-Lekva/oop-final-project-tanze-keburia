package servlets;

import classes.User;
import classes.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ServletContext context = request.getServletContext();
        UserDAO userDAO = (UserDAO)context.getAttribute("users");
        if(userDAO.userExists(username)) {
            response.sendRedirect("User with given username already exists");
        }
        else {
            User newUser = new User(username, password);
            userDAO.addUser(newUser);
            response.sendRedirect("loginSuccess.jsp");
        }
    }
}
