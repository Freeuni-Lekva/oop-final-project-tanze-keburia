package database;

import java.util.*;


public class FriendRequestDAO {

    private Map<String, List<String>> requests = new HashMap<>();

    public List<String> getRequestList(String a) {
        if(requests.containsKey(a)) return requests.get(a);

        return new ArrayList<>();
    }


}
