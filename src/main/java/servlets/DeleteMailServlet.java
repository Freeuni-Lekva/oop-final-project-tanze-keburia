package servlets;

import database.MailDAO;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteMail")
public class DeleteMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (idParam != null && username != null) {
            int mailId = Integer.parseInt(idParam);
            ServletContext context = getServletContext();
            MailDAO mailDAO = (MailDAO) context.getAttribute("mails");
            mailDAO.deleteMail(mailId, username);
        }
        response.sendRedirect("inbox.jsp");
    }
}
