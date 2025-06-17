import java.sql.Connection;
import java.util.HashMap;

public class UserDAO {
    private HashMap<String, String> accounts;
    public UserDAO(Connection con) {
        accounts = new HashMap<>();
    }
    public void addUser(User user){
        accounts.put(user.getUserName(), user.getPassword());
    }
    public boolean checkPassword(String userName, String password){
        if(accounts.containsKey(userName)){
            return accounts.get(userName).equals(password);
        }
        return false;
    }
}
