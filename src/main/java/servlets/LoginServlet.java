package servlets;

import classes.DAOConnecter;
import classes.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try(Connection conn = DAOConnecter.getConnection()){
            UserDAO dao = new UserDAO(conn);
           HttpSession session = request.getSession();
          session.setAttribute("username", username);
            if(dao.userExists(username) && dao.checkPassword(username, password)){
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("loginFailed.jsp");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
