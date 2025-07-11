package classes.mail;

import java.sql.Timestamp;

public class Mail {
    private int id;
    private String sender, receiver, subject, content;
    private Timestamp timestamp;
    private boolean isRead;
    public Mail(int id, String sender, String receiver, String subject, String content, Timestamp timestamp, boolean isRead) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public int getId() {return id;}
    public String getSender() {return sender;}
    public String getReceiver() {return receiver;}
    public String getSubject() {return subject;}
    public String getContent() {return content;}
    public Timestamp getTimestamp() {return timestamp;}
    public boolean isRead() { return isRead; }

}
