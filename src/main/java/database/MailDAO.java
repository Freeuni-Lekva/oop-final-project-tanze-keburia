package database;

import classes.Mail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailDAO {
    private Connection conn;
    public MailDAO(Connection conn) {
        this.conn = conn;
    }

    public void initialize() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS mails (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "sender VARCHAR(255) NOT NULL, " +
                    "receiver VARCHAR(255) NOT NULL, " +
                    "subject VARCHAR(255), " +
                    "content TEXT NOT NULL, " +
                    "sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "receiver_deleted BOOLEAN DEFAULT FALSE, " +
                    "sender_deleted BOOLEAN DEFAULT FALSE)");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize mails table", e);
        }
    }

    public void sendMail(Mail mail){
    if(mail == null ||mail.getSender() == null || mail.getReceiver() == null || mail.getSubject() == null || mail.getContent() == null){
        throw new IllegalArgumentException("Invalid mail fields");
    }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO mails (sender, receiver, subject, content) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, mail.getSender());
            ps.setString(2, mail.getReceiver());
            ps.setString(3, mail.getSubject());
            ps.setString(4, mail.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to send mail", e);
        }
    }

    public List<Mail> getInbox(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        List<Mail> inbox = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM mails WHERE receiver = ? AND receiver_deleted = FALSE ORDER BY sent_at DESC, id DESC")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    inbox.add(new Mail(
                            rs.getInt("id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("subject"),
                            rs.getString("content"),
                            rs.getTimestamp("sent_at")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get inbox", e);
        }
        return inbox;
    }


    public List<Mail> getSent(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        List<Mail> sent = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM mails WHERE sender = ? AND sender_deleted = FALSE ORDER BY sent_at DESC")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sent.add(new Mail(
                            rs.getInt("id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("subject"),
                            rs.getString("content"),
                            rs.getTimestamp("sent_at")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get sent mails", e);
        }

        return sent;
    }

    public void deleteMail(int mailId, String username) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE mails SET receiver_deleted = TRUE WHERE id = ? AND receiver = ?")) {
            ps.setInt(1, mailId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete mail", e);
        }
    }

    public void deleteSentMail(int mailId, String username) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE mails SET sender_deleted = TRUE WHERE id = ? AND sender = ?")) {
            ps.setInt(1, mailId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete sent mail", e);
        }
    }

}
