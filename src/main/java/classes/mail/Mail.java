package classes.mail;

import java.sql.Timestamp;

public class Mail {
    private int id;
    private String sender, receiver, subject, content;
    private Timestamp timestamp;
    public Mail(int id, String sender, String receiver, String subject, String content, Timestamp timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {return id;}
    public String getSender() {return sender;}
    public String getReceiver() {return receiver;}
    public String getSubject() {return subject;}
    public String getContent() {return content;}
    public Timestamp getTimestamp() {return timestamp;}

}
