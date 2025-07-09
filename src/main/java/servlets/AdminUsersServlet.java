package servlets;

import classes.Admins;
import database.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminUserServlet")
public class AdminUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Changed from "userDAO" to "users" to match ContextListener
            UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users");
            if (userDAO == null) {
                throw new ServletException("UserDAO not found in servlet context");
            }

            List<String> admins = Admins.getAdmins();
            List<String> allUsers = userDAO.getAllUsers();

            request.setAttribute("admins", admins);
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("currentUser", username);

            request.getRequestDispatcher("admin-users.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error loading users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Changed from "userDAO" to "users" to match ContextListener
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users");
        if (userDAO == null) {
            throw new ServletException("UserDAO not found in servlet context");
        }

        String action = request.getParameter("action");

        try {
            if ("removeUser".equals(action)) {
                String userToRemove = request.getParameter("userToRemove");
                if (userToRemove != null && !userToRemove.equals(username)) {
                    if (userDAO.userExists(userToRemove)) {
                        if (Admins.isAdmin(userToRemove)) {
                            Admins.removeAdmin(userToRemove);
                        }
                        if (userDAO.removeUser(userToRemove)) {
                            request.setAttribute("success", "User completely removed from system");
                        }
                    }
                }
            }
            // Rest of your post handling remains exactly the same...

            // Keep your existing redirect
            response.sendRedirect("AdminUserServlet");

        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }
}