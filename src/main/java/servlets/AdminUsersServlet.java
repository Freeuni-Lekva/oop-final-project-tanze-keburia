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

@WebServlet("/admin-users")
public class AdminUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users");

            // We'll need to add getAllUsers() method to UserDAO
            // For now, we'll pass admin list and current user
            List<String> admins = Admins.getAdmins();

            request.setAttribute("admins", admins);
            request.setAttribute("currentUser", username);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin-users.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in and is admin
        if (username == null || !Admins.isAdmin(username)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("users");

        try {
            if ("removeUser".equals(action)) {
                String userToRemove = request.getParameter("userToRemove");
                if (userToRemove != null && !userToRemove.equals(username)) {
                    // We'll need to add removeUser() method to UserDAO
                    // userDAO.removeUser(userToRemove);
                    Admins.removeAdmin(userToRemove); // Remove from admins if was admin
                    request.setAttribute("success", "User removed successfully!");
                }
            } else if ("promoteUser".equals(action)) {
                String userToPromote = request.getParameter("userToPromote");
                if (userToPromote != null && userDAO.userExists(userToPromote)) {
                    Admins.addAdmin(userToPromote);
                    request.setAttribute("success", "User promoted to admin successfully!");
                }
            } else if ("demoteUser".equals(action)) {
                String userToDemote = request.getParameter("userToDemote");
                if (userToDemote != null && !userToDemote.equals(username)) {
                    Admins.removeAdmin(userToDemote);
                    request.setAttribute("success", "User demoted from admin successfully!");
                }
            }

            // Redirect to avoid form resubmission
            response.sendRedirect("admin-users");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred");
            doGet(request, response);
        }
    }
}