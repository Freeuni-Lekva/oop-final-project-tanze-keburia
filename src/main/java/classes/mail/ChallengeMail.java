package classes.mail;


import classes.social.Challenge;

import java.sql.Timestamp;

public class ChallengeMail extends Mail{

    public ChallengeMail(Challenge x, Double points, Timestamp timestamp, int id) {
        super(id, x.getReceiver(), x.getSender(), x.getQuizName(), x.getReceiver() + " scored " + points.toString(), timestamp, false);
    }
}