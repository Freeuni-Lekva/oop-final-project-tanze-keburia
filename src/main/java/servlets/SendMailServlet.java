package servlets;

import classes.Mail;
import database.MailDAO;
import database.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        ServletContext context = getServletContext();
        MailDAO mailDAO = (MailDAO) context.getAttribute("mails");
        UserDAO userDAO = (UserDAO) context.getAttribute("users");
        if (receiver == null || receiver.isEmpty() || subject == null || content == null) {
            response.sendRedirect("compose.jsp");
            return;
        }
        if (!userDAO.userExists(receiver)) {
            String errorSubject = "Failed to deliver mail to '" + receiver + "'";
            String errorContent = "Your message was not sent because user '" + receiver + "' does not exist.";
            Mail errorMail = new Mail(0, "System", sender, errorSubject, errorContent, null);
            mailDAO.sendMail(errorMail);
            response.sendRedirect("homepage.jsp");
            return;
        }

        mailDAO.sendMail(new Mail(0, sender, receiver, subject, content, null));
        response.sendRedirect("sent.jsp");
    }
}
