package servlets.mail;

import classes.mail.Mail;
import database.database_connection.DatabaseConnector;
import database.social.MailDAO;
import database.user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/SendMail")
public class SendMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String sender = (String) session.getAttribute("username");
        String receiver = request.getParameter("receiver");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");


        if (receiver == null || receiver.isEmpty() || subject == null || content == null) {
            response.sendRedirect("compose.jsp");
            return;
        }
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            MailDAO mailDAO = new MailDAO(conn);

            if (!userDAO.userExists(receiver)) {
                String errorSubject = "Failed to deliver mail to '" + receiver + "'";
                String errorContent = "Your message was not sent because user '" + receiver + "' does not exist.";
                Mail errorMail = new Mail(0, "System", sender, errorSubject, errorContent, null, false);
                mailDAO.sendMail(errorMail);
                response.sendRedirect("Homepage");
                return;
            }

            mailDAO.sendMail(new Mail(0, sender, receiver, subject, content, null, false));
            response.sendRedirect("SentServlet");

        } catch (SQLException e) {
            throw new ServletException("Database error while sending mail", e);
        }
    }
}