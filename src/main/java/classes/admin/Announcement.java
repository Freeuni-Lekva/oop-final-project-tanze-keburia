package classes.admin;

import java.sql.Timestamp;

public class Announcement {
    private final String author;
    private String body;
    private final Timestamp publishDate;
    private final String id;
    public Announcement(String id, String author, String body, Timestamp publishDate) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.publishDate = publishDate;
    }
    public String getBody() {
        return body;
    }
    public String getAuthor() {
        return author;
    }
    public String getId() {
        return id;
    }
    public Timestamp getPublishDate(){
        return  publishDate;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return id.equals(that.id);
    }
}
