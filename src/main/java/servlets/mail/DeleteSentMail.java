package servlets.mail;

import classes.Mail;
import database.DatabaseConnector;
import database.MailDAO;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DeleteSentMail")
public class DeleteSentMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        if (idParam != null && username != null) {
            int mailId = Integer.parseInt(idParam);
            try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
                MailDAO mailDAO = new MailDAO(conn);
                // Check if mail belongs to user's sent mails
                List<Mail> sentMails = mailDAO.getSent(username);
                boolean ownsMail = false;
                for (Mail mail : sentMails) {
                    if (mail.getId() == mailId) {
                        ownsMail = true;
                        break;
                    }
                }

                if (ownsMail) {
                    mailDAO.deleteSentMail(mailId, username);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Database error while deleting sent mail", e);
            }
        }
        response.sendRedirect("SentServlet");
    }
}