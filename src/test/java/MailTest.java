import classes.Mail;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Timestamp;

public class MailTest extends TestCase {
    @Test
    public void test1(){
            int id = 1;
            String sender = "guga";
            String receiver = "ako";
            String subject = "proeqti";
            String content = "xodze xar?";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Mail mail = new Mail(id, sender, receiver, subject, content, timestamp);

            assertEquals(id, mail.getId());
            assertEquals(sender, mail.getSender());
            assertEquals(receiver, mail.getReceiver());
            assertEquals(subject, mail.getSubject());
            assertEquals(content, mail.getContent());
            assertEquals(timestamp, mail.getTimestamp());
    }
}
