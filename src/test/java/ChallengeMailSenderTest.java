import classes.mail.ChallengeMailSender;
import classes.mail.Mail;
import classes.social.Challenge;
import database.social.MailDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChallengeMailSenderTest {
    private static Connection conn;
    @BeforeAll
    public static void setUp() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    }
    @Test
    public void testSendMail() {
        MailDAO mails = new MailDAO(conn);
        mails.initialize();
        ChallengeMailSender sender = new ChallengeMailSender(mails);
        Challenge x = new Challenge("a", "b", "2", "A", 3.0);
        Timestamp now = new Timestamp(System.currentTimeMillis());
      //  ChallengeMail mail = new ChallengeMail(x, 2.0, now, 0);
        sender.sendMail(x, 2.0, now);
        List<Mail> inbox = mails.getInbox("a");
        assertEquals(1, inbox.size());
        List<Mail> sent = mails.getSent("b");
        assertEquals(1, sent.size());
    }
    @Test
    public void testNull() {
        MailDAO mails = new MailDAO(conn);
        mails.initialize();
        ChallengeMailSender sender = new ChallengeMailSender(mails);
        Challenge x = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        //  ChallengeMail mail = new ChallengeMail(x, 2.0, now, 0);
        sender.sendMail(x, 2.0, now);
        List<Mail> inbox = mails.getInbox("a");
        assertEquals(0, inbox.size());
        List<Mail> sent = mails.getSent("b");
        assertEquals(0, sent.size());
    }
}
