package classes.mail;

import classes.social.Challenge;
import database.social.MailDAO;

import java.sql.Timestamp;

public class ChallengeMailSender {

    private MailDAO mails;
    public ChallengeMailSender(MailDAO mails) {
        this.mails = mails;
    }

    public void sendMail(Challenge x, double score, Timestamp st) {
        if(x == null) return;
        ChallengeMail mail = new ChallengeMail(x, score, st, 0);
        mails.sendMail(mail);
    }

}
