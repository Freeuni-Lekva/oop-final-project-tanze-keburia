package servlets;
import classes.Mail;
import database.MailDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MessageHistoryServlet")
public class MessageHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String otherUser = request.getParameter("otherUser");
        if (otherUser == null || otherUser.trim().isEmpty()) {
            response.sendRedirect("inbox.jsp");
            return;
        }

        MailDAO mailDAO = (MailDAO) getServletContext().getAttribute("mails");
        List<Mail> receivedFromOther = mailDAO.getInbox(currentUser);

        List<Mail> conversation = new ArrayList<>();

        for (Mail mail : receivedFromOther) {
            if (mail.getSender().equals(otherUser)) {
                conversation.add(mail);
            }
        }
        conversation.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));

        request.setAttribute("conversation", conversation);
        request.setAttribute("otherUser", otherUser);
        request.setAttribute("currentUser", currentUser);

        RequestDispatcher dispatcher = request.getRequestDispatcher("messageList.jsp");
        dispatcher.forward(request, response);
    }
}