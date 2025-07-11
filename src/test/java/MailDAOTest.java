import classes.mail.Mail;
import database.database_connection.DatabaseConnector;
import database.social.MailDAO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MailDAOTest {
    private static Connection conn;
    private static MailDAO mailDAO;
    @BeforeClass
    public static void setUpClass() throws Exception {
       conn =  DatabaseConnector.getInstance().getConnection();

        mailDAO = new MailDAO(conn);
        mailDAO.initialize();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    @Before
    public void clearTable() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM mails");
        }
    }

    @Test
    public void testSendAndGetInbox() {
        Mail mail1 = new Mail(0, "tarashi", "ako", "Hala", "real goat", null, false);
        Mail mail2 = new Mail(0, "mzare", "ako", "test", "practice", null, false);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);

        List<Mail> inbox = mailDAO.getInbox("ako");

        assertEquals(2, inbox.size());
        assertEquals("mzare", inbox.get(0).getSender());
        assertEquals("tarashi", inbox.get(1).getSender());
    }

    @Test
    public void testGetSent() {
        Mail mail1 = new Mail(0, "guga", "nini", "present", "buy accessories for lizi", null, false);
        Mail mail2 = new Mail(0, "guga", "sandro", "present", "buy a watch for luka", null, false);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);

        List<Mail> sent = mailDAO.getSent("guga");

        assertEquals(2, sent.size());
        assertTrue(sent.stream().anyMatch(m -> m.getReceiver().equals("nini")));
        assertTrue(sent.stream().anyMatch(m -> m.getReceiver().equals("sandro")));
    }

    @Test
    public void testDeleteMail() {
        Mail mail1 = new Mail(0, "alice", "bob", "hello", "hi bob", null, false);
        Mail mail2 = new Mail(0, "alice", "bob", "second", "another message", null, false);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);

        List<Mail> inboxBefore = mailDAO.getInbox("bob");
        assertEquals(2, inboxBefore.size());

        int idToDelete = inboxBefore.get(0).getId();
        mailDAO.deleteMail(idToDelete, "bob");

        List<Mail> inboxAfter = mailDAO.getInbox("bob");
        assertEquals(1, inboxAfter.size());
        assertTrue(inboxAfter.stream().noneMatch(m -> m.getId() == idToDelete));
    }

    @Test
    public void testMailGetters() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Mail mail = new Mail(42, "alice", "bob", "hello", "hi bob", now, true);

        assertEquals(42, mail.getId());
        assertEquals("alice", mail.getSender());
        assertEquals("bob", mail.getReceiver());
        assertEquals("hello", mail.getSubject());
        assertEquals("hi bob", mail.getContent());
        assertEquals(now, mail.getTimestamp());
        assertTrue(mail.isRead());
    }
    @Test
    public void testDeleteSentMail() {
        Mail mail = new Mail(0, "davit", "mari", "update", "see you soon", null, false);
        mailDAO.sendMail(mail);

        List<Mail> sentBefore = mailDAO.getSent("davit");
        assertEquals(1, sentBefore.size());

        int mailId = sentBefore.get(0).getId();
        mailDAO.deleteSentMail(mailId, "davit");

        List<Mail> sentAfter = mailDAO.getSent("davit");
        assertTrue(sentAfter.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullSender() {
        Mail mail = new Mail(0, null, "receiver", "subject", "content", null, false);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullReceiver() {
        Mail mail = new Mail(0, "sender", null, "subject", "content", null, false);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullSubject() {
        Mail mail = new Mail(0, "sender", "receiver", null, "content", null, false);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullContent() {
        Mail mail = new Mail(0, "sender", "receiver", "subject", null, null, false);
        mailDAO.sendMail(mail);
    }

    @Test
    public void testEmptyInboxAndSent() {
        assertTrue(mailDAO.getInbox("nobody").isEmpty());
        assertTrue(mailDAO.getSent("nobody").isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInboxWithNullUsername() {
        mailDAO.getInbox(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSentWithNullUsername() {
        mailDAO.getSent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailNullMailObject() {
        mailDAO.sendMail(null);
    }

    @Test(expected = RuntimeException.class)
    public void testSendMailSQLException() throws SQLException {
        // Create a MailDAO with a closed connection to trigger SQLException
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);

        Mail mail = new Mail(0, "a", "b", "s", "c", null, false);
        badDAO.sendMail(mail); // should throw RuntimeException
    }

    @Test(expected = RuntimeException.class)
    public void testGetSentSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);
        badDAO.getSent("guga");
    }

    @Test(expected = RuntimeException.class)
    public void testGetInboxSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);
        badDAO.getInbox("ako");
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteMailSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);
        badDAO.deleteMail(1, "bob");
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteSentMailSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);
        badDAO.deleteSentMail(1, "davit");
    }

    @Test(expected = RuntimeException.class)
    public void testInitializeSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        MailDAO badDAO = new MailDAO(badConn);
        badDAO.initialize();
    }

    @Test
    public void testCountUnreadMails() {
        Mail mail1 = new Mail(0, "zaza", "gio", "subject1", "body1", null, false);
        Mail mail2 = new Mail(0, "nika", "gio", "subject2", "body2", null, false);
        Mail mail3 = new Mail(0, "sopo", "gio", "subject3", "body3", null, false);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);
        mailDAO.sendMail(mail3);

        List<Mail> inbox = mailDAO.getInbox("gio");
        int idToMarkRead = inbox.get(0).getId(); // Most recent one
        mailDAO.markAsRead(idToMarkRead, "gio");

        int unreadCount = mailDAO.countUnreadMails("gio");

        assertEquals(2, unreadCount);
    }


    @Test
    public void testMarkAsRead() {
        Mail mail = new Mail(0, "ana", "luka", "reminder", "project meeting", null, false);
        mailDAO.sendMail(mail);

        List<Mail> inbox = mailDAO.getInbox("luka");
        assertFalse(inbox.isEmpty());

        int mailId = inbox.get(0).getId();

        mailDAO.markAsRead(mailId, "luka"); // Updated to pass username

        // Re-fetch inbox to verify change
        List<Mail> updatedInbox = mailDAO.getInbox("luka");
        Mail updatedMail = updatedInbox.stream()
                .filter(m -> m.getId() == mailId)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedMail);
        assertTrue(updatedMail.isRead());
    }




}
