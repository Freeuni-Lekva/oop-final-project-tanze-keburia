import classes.Mail;
import database.DatabaseConnectionPull;
import database.DatabaseConnector;
import database.MailDAO;
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

public class MailDAOTest {
    private static Connection conn;
    private static MailDAO mailDAO;
    @BeforeClass
    public static void setUpClass() throws Exception {
        DatabaseConnector.getInstance(
                DatabaseConnectionPull.url,
                DatabaseConnectionPull.username,
                DatabaseConnectionPull.password
        );
        conn = DatabaseConnector.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS mails");
            stmt.execute("CREATE TABLE mails (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "sender VARCHAR(255) NOT NULL, " +
                    "receiver VARCHAR(255) NOT NULL, " +
                    "subject VARCHAR(255), " +
                    "content TEXT NOT NULL, " +
                    "sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "receiver_deleted BOOLEAN DEFAULT FALSE, " +
                    "sender_deleted BOOLEAN DEFAULT FALSE)");
        }

        mailDAO = new MailDAO(conn);
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
        Mail mail1 = new Mail(0, "tarashi", "ako", "Hala", "real goat", null);
        Mail mail2 = new Mail(0, "mzare", "ako", "test", "practice", null);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);

        List<Mail> inbox = mailDAO.getInbox("ako");

        assertEquals(2, inbox.size());
        assertEquals("mzare", inbox.get(0).getSender());
        assertEquals("tarashi", inbox.get(1).getSender());
    }

    @Test
    public void testGetSent() {
        Mail mail1 = new Mail(0, "guga", "nini", "present", "buy accessories for lizi", null);
        Mail mail2 = new Mail(0, "guga", "sandro", "present", "buy a watch for luka", null);

        mailDAO.sendMail(mail1);
        mailDAO.sendMail(mail2);

        List<Mail> sent = mailDAO.getSent("guga");

        assertEquals(2, sent.size());
        assertTrue(sent.stream().anyMatch(m -> m.getReceiver().equals("nini")));
        assertTrue(sent.stream().anyMatch(m -> m.getReceiver().equals("sandro")));
    }

    @Test
    public void testDeleteMail() {
        Mail mail1 = new Mail(0, "alice", "bob", "hello", "hi bob", null);
        Mail mail2 = new Mail(0, "alice", "bob", "second", "another message", null);

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
        Mail mail = new Mail(42, "alice", "bob", "hello", "hi bob", now);

        assertEquals(42, mail.getId());
        assertEquals("alice", mail.getSender());
        assertEquals("bob", mail.getReceiver());
        assertEquals("hello", mail.getSubject());
        assertEquals("hi bob", mail.getContent());
        assertEquals(now, mail.getTimestamp());
    }
    @Test
    public void testDeleteSentMail() {
        Mail mail = new Mail(0, "davit", "mari", "update", "see you soon", null);
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
        Mail mail = new Mail(0, null, "receiver", "subject", "content", null);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullReceiver() {
        Mail mail = new Mail(0, "sender", null, "subject", "content", null);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullSubject() {
        Mail mail = new Mail(0, "sender", "receiver", null, "content", null);
        mailDAO.sendMail(mail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMailWithNullContent() {
        Mail mail = new Mail(0, "sender", "receiver", "subject", null, null);
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

}
