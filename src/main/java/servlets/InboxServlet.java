package servlets;

import classes.Mail;
import database.DatabaseConnector;
import database.MailDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/InboxServlet")
public class InboxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            MailDAO mailDAO = new MailDAO(conn);
            List<Mail> inbox = mailDAO.getInbox(username);
            request.setAttribute("inbox", inbox);

            request.getRequestDispatcher("inbox.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading inbox", e);
        }
    }
}
