package servlets;

import com.sun.net.httpserver.HttpContext;
import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        ServletContext context = request.getServletContext();
        UserDAO userDAO = (UserDAO) context.getAttribute("users");
        if(userDAO.userExists(username)){
            request.setAttribute("foundUser", username);
            request.getRequestDispatcher("searchResult.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("noUserFound.jsp");
        }
    }
}
