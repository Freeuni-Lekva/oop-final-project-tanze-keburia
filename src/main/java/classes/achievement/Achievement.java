package classes.achievement;

import java.sql.Timestamp;

public class Achievement {
    private int id;
    private String username;
    private String type;
    private Timestamp awardedAt;

    public Achievement(int id, String username, String type, Timestamp awardedAt) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.awardedAt = awardedAt;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getType() { return type; }
    public Timestamp getAwardedAt() { return awardedAt; }
}
