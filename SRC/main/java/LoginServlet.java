package main.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try(Connection conn = DAOConnecter.getConnection()){
            UserDAO dao = new UserDAO(conn);
            boolean check = dao.checkPassword(username, password);
            if(check){

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
