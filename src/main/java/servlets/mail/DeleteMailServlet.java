package servlets.mail;

import database.database_connection.DatabaseConnector;
import database.social.MailDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeleteMail")
public class DeleteMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        if (idParam != null && username != null) {
            int mailId = Integer.parseInt(idParam);
            try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
                MailDAO mailDAO = new MailDAO(conn);
                mailDAO.deleteMail(mailId, username);
            } catch (SQLException e) {
                throw new RuntimeException("Database error while deleting mail", e);
            }
        }
        response.sendRedirect("InboxServlet");
    }
}
