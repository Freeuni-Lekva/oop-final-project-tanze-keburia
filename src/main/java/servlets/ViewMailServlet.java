package servlets;

import classes.Mail;
import database.database_connection.DatabaseConnector;
import database.MailDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ViewMail")
public class ViewMailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("InboxServlet");
            return;
        }

        int mailId;
        try {
            mailId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid mail ID.");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            MailDAO mailDAO = new MailDAO(conn);

            // Search in inbox
            Mail mail = null;
            for (Mail m : mailDAO.getInbox(username)) {
                if (m.getId() == mailId) {
                    mail = m;
                    break;
                }
            }

            if (mail == null) {
                for (Mail m : mailDAO.getSent(username)) {
                    if (m.getId() == mailId) {
                        mail = m;
                        break;
                    }
                }
            }

            if (mail == null) {
                response.getWriter().write("Message not found or access denied.");
                return;
            }

            request.setAttribute("mail", mail);
            request.getRequestDispatcher("message.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error retrieving mail", e);
        }
    }
}
