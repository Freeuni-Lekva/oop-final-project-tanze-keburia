package database;

import java.sql.Connection;
import java.util.*;


public class FriendRequestDAO {

    private Map<String, List<String>> requests = new HashMap<>();
    public FriendRequestDAO(Connection conn) {}
    public List<String> getRequestList(String a) {
        if(requests.containsKey(a)) return requests.get(a);

        return new ArrayList<>();
    }

    public void addRequest(String sender, String receiver) {
        if(!requests.containsKey(receiver)) requests.put(receiver, new ArrayList<>());

        requests.get(receiver).add(sender);
    }

    public void removeRequest(String sender, String receiver) {
        if(requests.containsKey(receiver))
            requests.get(receiver).remove(sender);
    }


}
