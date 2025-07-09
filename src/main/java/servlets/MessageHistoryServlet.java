package servlets;

import classes.Mail;
import database.DatabaseConnector;
import database.MailDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
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
        String messageType = request.getParameter("messageType");
        if (otherUser == null || otherUser.trim().isEmpty()) {
            response.sendRedirect("InboxServlet");
            return;
        }

        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            MailDAO mailDAO = new MailDAO(conn);
            List<Mail> filteredMessages = new ArrayList<>();

            if ("sent".equals(messageType)) {
                List<Mail> sentMessages = mailDAO.getSent(currentUser);
                for (Mail mail : sentMessages) {
                    if (mail.getReceiver().equals(otherUser)) {
                        filteredMessages.add(mail);
                    }
                }
            } else {
                List<Mail> receivedMessages = mailDAO.getInbox(currentUser);
                for (Mail mail : receivedMessages) {
                    if (mail.getSender().equals(otherUser)) {
                        filteredMessages.add(mail);
                    }
                }
            }

            filteredMessages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));

            request.setAttribute("conversation", filteredMessages);
            request.setAttribute("otherUser", otherUser);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("messageType", messageType);
            if ("sent".equals(messageType)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("sentMessageList.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("messageList.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e){
            throw new ServletException("Error retrieving message history", e);
        }
    }
}