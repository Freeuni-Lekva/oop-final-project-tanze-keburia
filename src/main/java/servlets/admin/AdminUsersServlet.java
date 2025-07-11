package servlets.admin;

import database.admin.Admins;
import database.database_connection.DatabaseConnector;
import database.user.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/AdminUserServlet")
public class AdminUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");

        if (currentUser == null || !Admins.isAdmin(currentUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            List<String> admins = Admins.getAdmins();
            List<String> allUsers = userDAO.getAllUsers();

            request.setAttribute("admins", admins);
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("currentUser", currentUser);

            request.getRequestDispatcher("admin-users.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");

        if (currentUser == null || !Admins.isAdmin(currentUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            String message = null;

            if ("removeUser".equals(action)) {
                String userToRemove = request.getParameter("userToRemove");
                if (userToRemove != null && !userToRemove.equals(currentUser)) {
                    if (userDAO.userExists(userToRemove)) {
                        if (Admins.isAdmin(userToRemove)) {
                            Admins.removeAdmin(userToRemove);
                        }
                        if (userDAO.removeUser(userToRemove)) {
                            message = "User removed successfully";
                        }
                    }
                }
            }
            else if ("promoteUser".equals(action)) {
                String userToPromote = request.getParameter("userToPromote");
                if (userToPromote != null && !userToPromote.equals(currentUser)) {
                    if (userDAO.userExists(userToPromote)) {
                        Admins.addAdmin(userToPromote);
                        userDAO.setAdminStatus(userToPromote, true);
                        message = "User promoted to admin successfully";
                    }
                }
            }
            else if ("demoteUser".equals(action)) {
                String userToDemote = request.getParameter("userToDemote");
                if (userToDemote != null && !userToDemote.equals(currentUser)) {
                    if (Admins.isAdmin(userToDemote)) {
                        Admins.removeAdmin(userToDemote);
                        userDAO.setAdminStatus(userToDemote, false);
                        message = "Admin rights removed successfully";
                    }
                }
            }

            if (message != null) {
                session.setAttribute("statusMessage", message);
            }

            response.sendRedirect("AdminUserServlet");

        } catch (SQLException e) {
            throw new ServletException("Error processing request", e);
        }
    }
}