package servlets;

import classes.Mail;
import database.MailDAO;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/DeleteSentMail")
public class DeleteSentMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (idParam != null && username != null) {
            int mailId = Integer.parseInt(idParam);
            ServletContext context = getServletContext();
            MailDAO mailDAO = (MailDAO) context.getAttribute("mails");
            //this way i check if mail i am deleting is really in logged in account's sent list
            // this is to avoid problems if someone tries to access mail with url manually
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
        }
        response.sendRedirect("sent.jsp");
    }
}
