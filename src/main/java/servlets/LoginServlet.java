package servlets;

import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ServletContext context = getServletContext();
        UserDAO userDAO = (UserDAO)context.getAttribute("users");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        if(userDAO.userExists(username) && userDAO.checkPassword(username, password)){
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("loginFailed.jsp");
        }

    }
}
