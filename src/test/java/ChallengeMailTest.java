import classes.mail.ChallengeMail;
import classes.mail.Mail;
import classes.social.Challenge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChallengeMailTest {
    @Test
    public void test1(){
        int id = 1;
        String sender = "guga";
        String receiver = "ako";
        String quiz_name = "proeqti";
        String content = "guga scored 2.0";
        Challenge x = new Challenge(receiver, sender, "2" , quiz_name, 3.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ChallengeMail mail = new ChallengeMail(x, 2.0, timestamp, 1);
        assertEquals(id, mail.getId());
        assertEquals(sender, mail.getSender());
        assertEquals(receiver, mail.getReceiver());
        assertEquals(quiz_name, mail.getSubject());
        assertEquals(content, mail.getContent());
        assertEquals(timestamp, mail.getTimestamp());
    }
}
