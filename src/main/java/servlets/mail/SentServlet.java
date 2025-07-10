package servlets.mail;

import classes.mail.Mail;
import database.database_connection.DatabaseConnector;
import database.social.MailDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/SentServlet")
public class SentServlet extends HttpServlet {
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
            List<Mail> sentMails = mailDAO.getSent(username);
            request.setAttribute("sentMails", sentMails);
            request.getRequestDispatcher("sent.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading sent mails", e);
        }
    }
}

