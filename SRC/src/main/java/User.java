import java.security.NoSuchAlgorithmException;

public class User {
    String userName;
    String password;

    public User(String userName, String password) throws NoSuchAlgorithmException {
        this.userName = userName;
        this.password = Hasher.hashPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
